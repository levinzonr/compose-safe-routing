package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import cz.levinzonr.saferoute.processor.codegen.extensions.*
import cz.levinzonr.saferoute.processor.constants.ClassNames
import cz.levinzonr.saferoute.processor.constants.Constants
import cz.levinzonr.saferoute.processor.constants.KDoc
import cz.levinzonr.saferoute.processor.extensions.ComposableFunction
import cz.levinzonr.saferoute.processor.extensions.asList
import cz.levinzonr.saferoute.processor.models.ModelData
import cz.levinzonr.saferoute.processor.models.NavGraphData
import cz.levinzonr.saferoute.processor.models.RouteData
import cz.levinzonr.saferoute.processor.pathbuilder.fullPathBuilder

internal class RoutesBuilder(val data: ModelData) {

    fun build(): TypeSpec {
        val mainGraph = data.navGraphs.first { it.name == "main" }
        val graphs = data.navGraphs.filterNot { it.name == "main" }
        return TypeSpec.objectBuilder(Constants.FILE_ROUTES)
            .addProperties(mainGraph.routes.map { it.toRouteProperty() })
            .addProperties(graphs.map { it.toRoutesProperty() })
            .deprecate(
                message ="Routes.kt is deprecated, please use GraphRoutes object or Routes directly",
                replaceWithExpression = "${mainGraph.graphName}Routes",
                ClassName(data.packageName, "${mainGraph.graphName}Routes")
            )
            .addKdoc(KDoc.ROUTES_SPEC)
            .build()
    }

    private fun NavGraphData.toRoutesProperty(): PropertySpec {
        val className = ClassName(data.packageName, "${graphName}Routes")
        return PropertySpec.builder(
            "${name.capitalize()}Routes",
            ClassName(data.packageName, "${graphName}Routes")
        )
            .initializer("%T", className)
            .build()
    }

}