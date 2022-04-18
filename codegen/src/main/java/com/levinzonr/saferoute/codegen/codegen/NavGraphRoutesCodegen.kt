package com.levinzonr.saferoute.codegen.codegen

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import com.levinzonr.saferoute.codegen.codegen.extensions.toRouteProperty
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.NavGraphData
import java.io.File


object NavGraphRoutesCodegen: FilesGen {

    override fun generate(data: ModelData): List<FileSpec> {
        return data.navGraphs.map { navGraphData ->
            FileSpec.get(data.packageName, createGraph(navGraphData))
        }
    }

    private fun createGraph(graphData: NavGraphData): TypeSpec {
        val builder = TypeSpec.objectBuilder("${graphData.graphName}Routes")
        val routeProperties = graphData.routes.map { it.toRouteProperty() }
        builder.addProperties(routeProperties)
        return builder.build()
    }
}