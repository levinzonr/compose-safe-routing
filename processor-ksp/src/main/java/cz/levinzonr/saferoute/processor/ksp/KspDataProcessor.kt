package cz.levinzonr.saferoute.processor.ksp

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.levinzonr.saferoute.codegen.codegen.extensions.checkNullable
import com.levinzonr.saferoute.codegen.core.DataProcessor
import com.levinzonr.saferoute.codegen.models.*
import java.lang.IllegalArgumentException

@OptIn(KspExperimental::class)
class KspDataProcessor(
    private val elements: List<KSFunctionDeclaration>,
    private val resolver: Resolver
) : DataProcessor {

    private val packageName = elements.first().packageName.asString()


    override fun process(): ModelData? {
        val routes = elements.map { element ->
            val annotation =
                element.annotations.filter { it.shortName.asString().contains("Route") }
            annotation.map { it.process(element) }.toList()
        }.flatten()


        val graphs = routes.groupBy { it.navGraphName }.map {
            NavGraphData(
                name = it.key,
                routes = it.value,
                start = requireNotNull(it.value.find { it.start }) { "Graph ${it.key} has no start property defined" }
            )
        }

        return ModelData(
            packageName = packageName,
            navGraphs = graphs
        )
    }


    private fun KSAnnotation.process(element: KSFunctionDeclaration): RouteData {
        return RouteData(
            name = fieldByName<String>("name").takeIf { it != "@null" }
                ?: element.simpleName.asString(),
            arguments = processArguments(),
            packageName = element.packageName.asString(),
            deeplinks = processDeeplinks(),
            routeTransition = fieldByName<KSType>("transition").declaration.qualifiedName?.asString(),
            contentName = element.toString(),
            params = element.parameters.mapNotNull { it.name?.asString() },
            navGraphName = fieldByName<KSAnnotation>("navGraph").fieldByName("name") ?: "main",
            start = fieldByName<KSAnnotation>("navGraph").fieldByName("start") ?: false
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