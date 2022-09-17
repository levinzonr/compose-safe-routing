package com.levinzonr.saferoute.codegen.codegen

import com.levinzonr.saferoute.codegen.codegen.extensions.buildComposableFunction
import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.core.AnnotationsResolver
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.core.GeneratorUnit
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.NavGraphData
import com.levinzonr.saferoute.codegen.models.RouteData
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

class NavGraphScopesCodegen(
    private val annotationsResolver: AnnotationsResolver
) : FilesGen {

    override fun generate(data: ModelData): List<GeneratorUnit> {
        return data.navGraphs.map {
            listOf(
                generateNavScope(it),
                generateNavScopeImplementation(it)
            )
        }.flatten()
    }

    private fun generateNavScope(graphData: NavGraphData): GeneratorUnit {
        val fileSpecBuilder = FileSpec.get(
            graphData.packageName, TypeSpec.interfaceBuilder(graphData.scopeClassName)
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
        graphData: NavGraphData
    ): GeneratorUnit {

        val property = PropertySpec.builder("navGraphBuilder", ClassNames.NavGraphBuilder)
            .initializer("navGraphBuilder")
            .addModifiers(KModifier.PRIVATE)

        val typeBuilder = TypeSpec.classBuilder(graphData.scopeBuilderClassName)
            .addSuperinterface(graphData.scopeClassName)
            .addModifiers(KModifier.INTERNAL)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(
                        name = "navGraphBuilder", ClassNames.NavGraphBuilder
                    ).build()
            )
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

        return GeneratorUnit(FileSpec.get(graphData.packageName, typeBuilder.build()), graphData.sources)
    }

    private fun RouteData.toFunSpecBuilder(): FunSpec.Builder {
        val annotations = annotationsResolver.resolve(this)
        val function = buildComposableFunction(
            params = listOf(
                ParameterSpec.builder("entry", ClassNames.NavBackStackEntry).build()
            )
        )
        val parameterSpec = ParameterSpec.builder("content", function).build()
        return FunSpec
            .builder(name.replaceFirstChar { it.lowercase() })
            .addParameter(parameterSpec)
            .apply { annotations.forEach { addAnnotation(it) } }
    }
}