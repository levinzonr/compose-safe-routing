package com.levinzonr.saferoute.codegen.codegen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.core.GeneratorUnit
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.RouteData

object RouteArgsProviderCodegen : FilesGen {

    override fun generate(data: ModelData): List<GeneratorUnit> {
        return data.routes.map { route ->
            val fileSpec = FileSpec.builder(route.argsPackageName, "Local${route.argumentsName}")
                .addProperty(route.createLocalArgsProperty())
                .build()

            GeneratorUnit(
                fileSpec,
                listOf(route.source)
            )
        }
    }

    private fun RouteData.createLocalArgsProperty(): PropertySpec {
        return PropertySpec.builder(
            name = "Local${argumentsName}",
            type = ClassNames.ProvidableCompositionLocal.parameterizedBy(argsTypeClassName)
        ).initializer(
            InitTemplate,
            ClassNames.compositionLocalOf,
            argsTypeClassName,
            argumentsName + " are not provided"
        ).build()
    }

    private val InitTemplate = "%T<%T> {\n" +
            "    error(%S)\n" +
            "}"
}