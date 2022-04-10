package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import cz.levinzonr.saferoute.processor.codegen.extensions.*
import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.constants.KDoc
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.NavGraphData

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