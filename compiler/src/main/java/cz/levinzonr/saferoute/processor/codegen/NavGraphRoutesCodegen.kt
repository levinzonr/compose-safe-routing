package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import cz.levinzonr.saferoute.processor.codegen.extensions.toRouteProperty
import cz.levinzonr.saferoute.processor.models.ModelData
import cz.levinzonr.saferoute.processor.models.NavGraphData
import java.io.File


internal object NavGraphRoutesCodegen {

    fun generate(data: ModelData, file: File) {
        data.navGraphs.forEach { graph ->
            FileSpec.get(data.packageName, createGraph(graph))
                .writeTo(file)

        }
    }

    private fun createGraph(graphData: NavGraphData): TypeSpec {
        val builder = TypeSpec.objectBuilder("${graphData.graphName}Routes")
        val routeProperties = graphData.routes.map { it.toRouteProperty() }
        builder.addProperties(routeProperties)
        return builder.build()
    }
}