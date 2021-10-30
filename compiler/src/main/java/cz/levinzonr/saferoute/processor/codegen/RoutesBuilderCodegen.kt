package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
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

    private fun RouteData.createBuilderFun() : FunSpec {

        val cals = if (routeBuilderType.toString() == ClassNames.DefaultRouteBuilder.canonicalName) {
            ClassNames.composableBuilder
        } else {
            throw IllegalArgumentException("Invalid builder: ${routeBuilderType.toString()}")
        }

        val code = CodeBlock.Builder()
            .beginControlFlow("%T(Routes.${name.capitalize()})", cals)
            .addStatement("content()")
            .endControlFlow()

        return FunSpec.builder("${name}Route")
            .receiver(ClassNames.NavGraphBuilder)
            .addParameter("content", ComposableFunction)
            .addCode(code.build())
            .build()
    }
}