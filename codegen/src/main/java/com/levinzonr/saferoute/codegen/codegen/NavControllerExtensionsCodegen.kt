package com.levinzonr.saferoute.codegen.codegen

import com.levinzonr.saferoute.codegen.codegen.extensions.deprecate
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.levinzonr.saferoute.codegen.codegen.extensions.toParamSpec
import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.core.GeneratorUnit
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.RouteData


object NavControllerExtensionsCodegen : FilesGen {

    override fun generate(data: ModelData): List<GeneratorUnit> {
        val builder = FileSpec.builder(data.packageName, "NavController+RouteActions")
        data.routes.forEach { builder.addFunction(createActionFunSpec(it)) }

        return listOf(
            GeneratorUnit(
                fileSpec = builder.build(),
                sources = data.sources
            )
        )
    }

    private fun createActionFunSpec(route: RouteData): FunSpec {
        val arguments = route.arguments.joinToString(",") { it.name }
        return FunSpec.builder("navigateTo${route.name.capitalize()}")
            .addParameters(route.arguments.map { it.toParamSpec() })
            .receiver(ClassNames.NavController)
            .deprecate(
                message = "RouteActions.kt is deprecated, please use Route object and its invoke() operator",
                replaceWithExpression = "navigateTo(${route.specName}())",
                imports = arrayOf(route.specClassName, ClassNames.navigateTo)
            )
            .returns(Unit::class)
            .addCode("navigate(RoutesActions.to${route.name.capitalize()}($arguments))")
            .build()
    }
}