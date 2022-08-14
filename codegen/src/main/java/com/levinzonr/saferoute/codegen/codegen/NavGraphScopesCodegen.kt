package com.levinzonr.saferoute.codegen.codegen

import com.levinzonr.saferoute.codegen.codegen.extensions.ComposableFunction
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.core.GeneratorUnit
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.NavGraphData
import com.levinzonr.saferoute.codegen.models.RouteData
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec

object NavGraphScopesCodegen : FilesGen {

    override fun generate(data: ModelData): List<GeneratorUnit> {
       return data.navGraphs.map { generateNavScope(data.packageName, it) }
    }

    private fun generateNavScope(packageName: String, graphData: NavGraphData): GeneratorUnit {
        val fileSpecBuilder = FileSpec.get(
            packageName, TypeSpec.interfaceBuilder("${graphData.graphName}Scope")
                .apply {
                    graphData.routes.forEach {
                        addFunction(it.toFunSpec())
                    }
                }
                .build()
        )
        return GeneratorUnit(
            fileSpec = fileSpecBuilder,
            sources = graphData.sources
        )
    }

    private fun RouteData.toFunSpec() : FunSpec {
        val parameterSpec = ParameterSpec.builder("content", ComposableFunction).build()
        return FunSpec
            .builder(name.replaceFirstChar { it.lowercase() })
            .addParameter(parameterSpec)
            .addModifiers(KModifier.ABSTRACT)
            .build()
    }
}