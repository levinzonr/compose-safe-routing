package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import cz.levinzonr.saferoute.processor.constants.ClassNames
import cz.levinzonr.saferoute.processor.extensions.ComposableFunction
import cz.levinzonr.saferoute.processor.models.ModelData
import cz.levinzonr.saferoute.processor.models.RouteData
import java.io.File

internal class RoutesBuildersCodegen(val data: ModelData) {

    fun generate(output: File) {
        val fileSpec = FileSpec.builder(data.packageName, "NavGraphBuilder+Routes")
        data.routes.forEach { fileSpec.addFunction(it.createBuilderFun()) }
        fileSpec.build().writeTo(output)
    }

    private fun RouteData.createBuilderFun(): FunSpec {

        val code = CodeBlock.Builder()
            .beginControlFlow(this)
            .addStatement("content()")
            .endControlFlow()

        return FunSpec.builder("${name}Route")
            .receiver(ClassNames.NavGraphBuilder)
            .addParameter("content", ComposableFunction)
            .addAnnotation(this)
            .addCode(code.build())
            .build()
    }

    private fun CodeBlock.Builder.beginControlFlow(routeData: RouteData): CodeBlock.Builder {
        val routeSpec = "Routes.${routeData.name.capitalize()}"
        return when (routeData.routeTransition.toString()) {
            ClassNames.DefaultRouteTransition.canonicalName -> {
                beginControlFlow("%T($routeSpec)", ClassNames.composableTransition)
            }
            ClassNames.BottomSheetRouteTransition.canonicalName -> {
                beginControlFlow("%T($routeSpec)", ClassNames.bottomSheetTransition)
            }
            else -> {
                beginControlFlow(
                    "%T($routeSpec, transition = %T)",
                    ClassNames.animatedComposableTransition,
                    routeData.routeTransition?.asTypeName()
                )
            }
        }
    }

    private fun FunSpec.Builder.addAnnotation(routeData: RouteData) : FunSpec.Builder {
        return when (routeData.routeTransition.toString()) {
            ClassNames.DefaultRouteTransition.canonicalName -> {
                this
            }
            ClassNames.BottomSheetRouteTransition.canonicalName -> {
                addAnnotation(ClassNames.ExperimentalNavigationApi)
            }
            else -> addAnnotation(ClassNames.ExperimentalAnimationApi)
        }
    }

}