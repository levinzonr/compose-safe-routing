package com.levinzonr.saferoute.codegen.codegen

import com.levinzonr.saferoute.codegen.codegen.extensions.ComposableFunction
import com.squareup.kotlinpoet.*
import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.core.TypeHelper
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.RouteData
import java.io.File


class RoutesTransitionsCodegen(val typeHelper: TypeHelper) : FilesGen {

    override fun generate(data: ModelData): List<FileSpec> {
        val fileSpec = FileSpec.builder(data.packageName, "NavGraphBuilder+Routes")
        data.routes.forEach { fileSpec.addFunction(it.createBuilderFun()) }
        return listOf(fileSpec.build())
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
        beginControlFlow("%T(%T, %T)", ClassNames.route, routeData.specClassName, routeData.routeTransitionClass)
        return this
    }


    private fun FunSpec.Builder.addAnnotation(data: RouteData): FunSpec.Builder {
        val superTypes = typeHelper.superTypes(data.routeTransitionType)

        val hasAnimation =
            superTypes.find { it == ClassNames.AnimatedRouteTransition.canonicalName } != null
        val hasBottomSheet =
            superTypes.find { it == ClassNames.BottomSheetRouteTransition.canonicalName } != null
        val annotations = listOfNotNull(
            ClassNames.ExperimentalAnimationApi.takeIf { hasAnimation },
            ClassNames.ExperimentalNavigationApi.takeIf { hasBottomSheet || data.routeTransitionClass == ClassNames.BottomSheetRouteTransition }
        )

        annotations.forEach {
           addAnnotation(it)
        }

        return this
    }
}