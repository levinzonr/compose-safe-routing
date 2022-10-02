package cz.levinzonr.saferoute.processor.ksp

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.levinzonr.saferoute.codegen.codegen.extensions.checkNullable
import com.levinzonr.saferoute.codegen.core.DataProcessor
import com.levinzonr.saferoute.codegen.core.LogLevel
import com.levinzonr.saferoute.codegen.core.Logger
import com.levinzonr.saferoute.codegen.core.Source
import com.levinzonr.saferoute.codegen.models.ArgumentData
import com.levinzonr.saferoute.codegen.models.ArgumentType
import com.levinzonr.saferoute.codegen.models.DeeplinkData
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.ModelDataBuilder
import com.levinzonr.saferoute.codegen.models.NavGraphData
import com.levinzonr.saferoute.codegen.models.NavGraphInfo
import com.levinzonr.saferoute.codegen.models.OptionalArgData
import com.levinzonr.saferoute.codegen.models.RouteData
import java.lang.IllegalArgumentException
import kotlin.math.log

internal class KspDataProcessor(
    private val graphs: List<KSAnnotated>,
    private val elements: List<KSFunctionDeclaration>,
    private val resolver: Resolver,
    private val packageName: String,
    private val logger: Logger
) : DataProcessor {

    override fun process(): ModelData? {

        val dataBuilder = ModelDataBuilder()
        graphs.map { it as KSDeclaration }.map {
            dataBuilder.addGraph(
                it.simpleName.asString(),
                it.packageName.asString(),
                it.getSource()
            )
        }


        elements.forEach { element ->
            val routeAnnotation = element.annotations.first { it.shortName.asString() == "Route" }
            val route = routeAnnotation.process(element)

            val graphs = element.annotations.findGraphs(dataBuilder.graphs)
            dataBuilder.addRoute(route, graphs)
        }

        return dataBuilder.build(packageName).also {
            logger.log("Data: $it", level = LogLevel.Warning)
        }
    }

    private fun Sequence<KSAnnotation>.findGraphs(graphs: List<ModelDataBuilder.Graph>): List<ModelDataBuilder.RouteGraph> {
        val graphs =
            filter { ksAnnotation -> graphs.any { ksAnnotation.shortName.asString() == it.name } }.toList()
        return graphs.map {
            ModelDataBuilder.RouteGraph(
                name = it.shortName.asString(),
                start = it.fieldByName("start")
            )
        }
    }

    private fun KSAnnotation.process(element: KSFunctionDeclaration): RouteData {
        return RouteData(
            name = fieldByName<String>("name").takeIf { it != "@null" }
                ?: element.simpleName.asString(),
            arguments = processArguments(),
            packageName = element.packageName.asString(),
            deeplinks = processDeeplinks(),
            routeTransitionType = fieldByName<KSType>("transition").declaration,
            routeTransitionClassName = fieldByName<KSType>("transition").declaration.qualifiedName?.asString(),
            contentName = element.toString(),
            params = element.parameters.mapNotNull { it.name?.asString() },
            source = element.getSource()
        )
    }

    private fun KSDeclaration.getSource(): Source {
        return Source(
            filename = containingFile!!.fileName,
            filepath = containingFile!!.filePath,
            packageName = packageName.asString()
        )
    }

    private fun KSAnnotation.processDeeplinks(): List<DeeplinkData> {
        return fieldByName<List<KSAnnotation>>("deepLinks").map { annotation ->
            DeeplinkData(
                uriPattern = annotation.fieldByName<String>("pattern").checkNullable(),
                action = annotation.fieldByName<String>("action").checkNullable(),
                mimeType = annotation.fieldByName<String>("mimeType").checkNullable()
            )
        }
    }

    private fun KSAnnotation.processArguments(): List<ArgumentData> {
        return fieldByName<List<KSAnnotation>>("args").map { arg ->
            val type = arg.fieldByName<KSType>("type").asArgumentType()
            val isNullable = arg.fieldByName<Boolean>("isNullable")
            val defaultValue = arg.fieldByName<String>("defaultValue")
            val isOptional = arg.fieldByName<Boolean>("isOptional")
            val name = arg.fieldByName<String>("name")
            ArgumentData(
                name = name,
                type = type,
                isNullable = isNullable,
                optionalData = OptionalArgData.build(
                    type,
                    defaultValue,
                    isNullable,
                    isOptional,
                    name
                )
            )
        }
    }

    private fun KSType.asArgumentType(): ArgumentType {
        val builtIns = resolver.builtIns
        return when (this) {
            builtIns.intType -> ArgumentType.IntType
            builtIns.stringType -> ArgumentType.StringType
            builtIns.booleanType -> ArgumentType.BooleanType
            builtIns.longType -> ArgumentType.LongType
            builtIns.floatType -> ArgumentType.FloatType
            else -> throw IllegalArgumentException("Type ${toString()} not supported")
        }
    }
}

fun <T> KSAnnotation.fieldByName(name: String): T {
    return arguments.find { it.name?.asString() == name }?.value as T
}
