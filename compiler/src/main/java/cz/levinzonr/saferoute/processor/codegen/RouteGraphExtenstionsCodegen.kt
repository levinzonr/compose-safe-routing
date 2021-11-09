package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import cz.levinzonr.saferoute.processor.constants.ClassNames
import cz.levinzonr.saferoute.processor.extensions.ComposableFunction
import cz.levinzonr.saferoute.processor.models.ModelData
import cz.levinzonr.saferoute.processor.models.NavGraphData
import cz.levinzonr.saferoute.processor.typehelper.TypeHelper
import java.io.File
import javax.lang.model.type.TypeMirror


internal class RouteGraphExtenstionsCodegen(
    private val data: ModelData,
    private val typeHelper: TypeHelper<TypeMirror>
) {

    fun generate(dir: File) {
        val builder = FileSpec.builder(data.packageName, "NavGraphBuilder+Navigation")

        data.navGraphs.forEach { builder.addFunction(it.createBuilderSpec()) }

        return builder.build().writeTo(dir)
    }

    private fun NavGraphData.createBuilderSpec(): FunSpec {
        val builder = FunSpec.builder("navigation${name.capitalize()}")
            .receiver(ClassNames.NavGraphBuilder)

        routes.mapNotNull { it.routeTransition }
            .map { typeHelper.resolveNeededAnnotations(it) }
            .flatten()
            .distinctBy { it.canonicalName }
            .forEach { builder.addAnnotation(it) }

        val codeBlock = CodeBlock.builder()

        routes.forEach { routeData ->
            val param =
                with(ParameterSpec.builder("${routeData.name}Content", ComposableFunction)) {
                    if (routeData.params.isEmpty()) defaultValue("{ %T() }", routeData.contentClassName)
                    build()
                }

            builder.addParameter(param)

            codeBlock.beginControlFlow(routeData.builderName)
                .addStatement("${param.name}()")
                .endControlFlow()

        }

        builder.addCode(codeBlock.build())



        return builder.build()
    }
}