package com.levinzonr.saferoute.codegen.codegen

import com.levinzonr.saferoute.codegen.codegen.extensions.ComposableFunction
import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.core.GeneratorUnit
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.NavGraphData
import com.levinzonr.saferoute.codegen.models.RouteData
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

object NavGraphScopesCodegen : FilesGen {

    override fun generate(data: ModelData): List<GeneratorUnit> {
        return data.navGraphs.map {
            listOf(
                generateNavScope(data.packageName, it),
                generateNavScopeImplementation(data.packageName, it)
            )
        }.flatten()
    }

    private fun generateNavScope(packageName: String, graphData: NavGraphData): GeneratorUnit {
        val fileSpecBuilder = FileSpec.get(
            packageName, TypeSpec.interfaceBuilder("${graphData.graphName}Scope")
                .apply {
                    graphData.routes.forEach {
                        addFunction(it.toFunSpecBuilder().addModifiers(KModifier.ABSTRACT).build())
                    }
                }
                .build()
        )
        return GeneratorUnit(
            fileSpec = fileSpecBuilder,
            sources = graphData.sources
        )
    }


    private fun generateNavScopeImplementation(
        packageName: String,
        graphData: NavGraphData
    ): GeneratorUnit {

        val property = PropertySpec.builder("navGraphBuilder", ClassNames.NavGraphBuilder)
            .initializer("navGraphBuilder")
            .addModifiers(KModifier.PRIVATE)

        val typeBuilder = TypeSpec.classBuilder("${graphData.graphName}Builder")
            .addSuperinterface(ClassName(packageName, "${graphData.graphName}Scope"))
            .addModifiers(KModifier.INTERNAL)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(
                        name = "navGraphBuilder", ClassNames.NavGraphBuilder
                    ).build())
            .addProperty(property.build())



        graphData.routes.forEach {
            val funSpecBuilder = it.toFunSpecBuilder().addModifiers(KModifier.OVERRIDE)
            funSpecBuilder.addCode(
                CodeBlock.of(
                    "navGraphBuilder.%T(%T, %T, content)",
                    ClassNames.route,
                    it.routeClassName,
                    it.routeTransitionClass
                )
            )
            typeBuilder.addFunction(funSpecBuilder.build())
        }

        return GeneratorUnit(FileSpec.get(packageName, typeBuilder.build()), graphData.sources)
    }

    private fun RouteData.toFunSpecBuilder(): FunSpec.Builder {
        val parameterSpec = ParameterSpec.builder("content", ComposableFunction).build()
        return FunSpec
            .builder(name.replaceFirstChar { it.lowercase() })
            .addParameter(parameterSpec)
    }
}