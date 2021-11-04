package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import cz.levinzonr.saferoute.processor.constants.ClassNames
import cz.levinzonr.saferoute.processor.extensions.ComposableFunction
import cz.levinzonr.saferoute.processor.logger.LogLevel
import cz.levinzonr.saferoute.processor.logger.Logger
import cz.levinzonr.saferoute.processor.models.ModelData
import cz.levinzonr.saferoute.processor.models.RouteData
import jdk.dynalink.linker.support.TypeUtilities
import java.io.File
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.type.TypeMirror
import kotlin.math.log

internal class RoutesTransitionsCodegen(
    private val data: ModelData,
    private val processingEnvironment: ProcessingEnvironment,
    private val logger: Logger
) {

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

        val parameterSpec = ParameterSpec.builder("content", ComposableFunction).apply {
            if (params.isEmpty()) defaultValue("{ %T() }", contentClassName)
        }.build()

        return FunSpec.builder("${name}Route")
            .receiver(ClassNames.NavGraphBuilder)
            .addParameter(parameterSpec)
            .addAnnotation(this)
            .addCode(code.build())
            .build()
    }

    private fun CodeBlock.Builder.beginControlFlow(routeData: RouteData): CodeBlock.Builder {
        beginControlFlow("%T(${routeData.specName}, %T)", ClassNames.route, routeData.routeTransition)
        return this
    }


    private fun FunSpec.Builder.addAnnotation(data: RouteData): FunSpec.Builder {
        data.routeTransition?.let {
            val superTypes = processingEnvironment.typeUtils.directSupertypes(it)
            val hasAnimation = superTypes.find { it.toString() == ClassNames.AnimatedRouteTransition.canonicalName } != null
            val hasBottomSheet = superTypes.find { it.toString() == ClassNames.BottomSheetRouteTransition.canonicalName } != null
            logger.log(" ${ClassNames.AnimatedRouteTransition.canonicalName} Types of ${data.name}: ${superTypes.joinToString { it.toString() }}", level = LogLevel.Warning)
            if (hasAnimation) {
                addAnnotation(ClassNames.ExperimentalAnimationApi)
            }
            if (hasBottomSheet || it.toString() == ClassNames.BottomSheetRouteTransition.canonicalName) {
                addAnnotation(ClassNames.ExperimentalNavigationApi)
            }
        }
        return this
    }
}