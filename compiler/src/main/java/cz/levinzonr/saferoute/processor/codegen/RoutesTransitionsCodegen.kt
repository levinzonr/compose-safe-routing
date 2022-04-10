package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import com.levinzonr.saferoute.codegen.constants.ClassNames
import cz.levinzonr.saferoute.processor.extensions.ComposableFunction
import cz.levinzonr.saferoute.processor.logger.Logger
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.RouteData
import cz.levinzonr.saferoute.processor.typehelper.TypeHelper
import java.io.File
import javax.lang.model.type.TypeMirror

internal class RoutesTransitionsCodegen(
    private val data: ModelData,
    private val typeHelper: TypeHelper<TypeMirror>,
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

        return FunSpec.builder(builderName)
            .receiver(ClassNames.NavGraphBuilder)
            .addParameter(parameterSpec)
            .addAnnotation(this)
            .addCode(code.build())
            .build()
    }

    private fun CodeBlock.Builder.beginControlFlow(routeData: RouteData): CodeBlock.Builder {
        beginControlFlow("%T(%T, %T)", ClassNames.route, routeData.specClassName, routeData.routeTransition)
        return this
    }


    private fun FunSpec.Builder.addAnnotation(data: RouteData): FunSpec.Builder {
        data.routeTransition?.let {
            val annotations = typeHelper.resolveNeededAnnotations(it)
            annotations.forEach {
                addAnnotation(it)
            }
        }
        return this
    }
}