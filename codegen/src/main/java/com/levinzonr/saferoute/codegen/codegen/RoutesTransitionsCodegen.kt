package com.levinzonr.saferoute.codegen.codegen

import com.levinzonr.saferoute.codegen.codegen.extensions.ComposableFunction
import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.core.AnnotationsResolver
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.core.GeneratorUnit
import com.levinzonr.saferoute.codegen.core.TypeHelper
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.RouteData
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec

class RoutesTransitionsCodegen(
    private val annotationsResolver: AnnotationsResolver
) : FilesGen {

    override fun generate(data: ModelData): List<GeneratorUnit> {
        return data.routes.map {
            GeneratorUnit(
                sources = listOf(it.source),
                fileSpec = FileSpec.builder(it.packageName, "NavGraphBuilder+${it.name}")
                    .addFunction(it.createBuilderFun())
                    .build()
            )
        }
    }

    private fun RouteData.createBuilderFun(): FunSpec {

        val code = CodeBlock.Builder()
            .beginControlFlow(this)
            .addStatement("content()")
            .endControlFlow()

        val parameterSpec = ParameterSpec.builder("content", ComposableFunction).apply {
            if (params.isEmpty()) defaultValue("{ %T() }", contentClassName)
        }.build()

        return FunSpec.builder(builderName)
            .receiver(ClassNames.NavGraphBuilder)
            .addParameter(parameterSpec)
            .addAnnotation(this)
            .addCode(code.build())
            .build()
    }

    private fun CodeBlock.Builder.beginControlFlow(routeData: RouteData): CodeBlock.Builder {
        beginControlFlow(
            "%T(%T, %T)",
            ClassNames.route,
            routeData.specClassName,
            routeData.routeTransitionClass
        )
        return this
    }

    private fun FunSpec.Builder.addAnnotation(data: RouteData): FunSpec.Builder {
        val annotations = annotationsResolver.resolve(data)
        annotations.forEach {
            addAnnotation(it)
        }
        return this
    }
}
