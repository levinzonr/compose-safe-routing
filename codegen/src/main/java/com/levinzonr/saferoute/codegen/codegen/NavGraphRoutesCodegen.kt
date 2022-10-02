package com.levinzonr.saferoute.codegen.codegen

import com.levinzonr.saferoute.codegen.codegen.extensions.toRouteProperty
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.core.GeneratorUnit
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.NavGraphData
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec

object NavGraphRoutesCodegen : FilesGen {

    override fun generate(data: ModelData): List<GeneratorUnit> {
        return data.navGraphs.map { navGraphData ->
            GeneratorUnit(
                fileSpec = FileSpec.get(navGraphData.packageName, createGraph(navGraphData)),
                sources = navGraphData.routes.map { it.source }
            )
        }
    }

    private fun createGraph(graphData: NavGraphData): TypeSpec {
        val builder = TypeSpec.objectBuilder("${graphData.graphName}Routes")
        val routeProperties = graphData.routes.map { it.toRouteProperty() }
        builder.addProperties(routeProperties)
        return builder.build()
    }
}
