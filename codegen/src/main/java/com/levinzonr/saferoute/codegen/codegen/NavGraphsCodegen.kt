package com.levinzonr.saferoute.codegen.codegen

import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.core.GeneratorUnit
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.NavGraphData
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.TypeSpec

object NavGraphsCodegen : FilesGen {

    override fun generate(data: ModelData): List<GeneratorUnit> {
        return data.navGraphs.map {
            GeneratorUnit(
                FileSpec.get(data.packageName, it.createSpecProperty()),
                it.sources
            )
        }
    }

    private fun NavGraphData.createSpecProperty(): TypeSpec {
        return TypeSpec.objectBuilder(graphName)
            .addSuperinterface(ClassNames.RouteGraphSpec)
            .addProperty(
                PropertySpec.builder("name", String::class, KModifier.OVERRIDE)
                    .initializer("%S", graphName)
                    .build()
            )
            .addProperty(
                PropertySpec.builder("start", ClassNames.RouteSpec.parameterizedBy(STAR), KModifier.OVERRIDE)
                    .initializer("%T", start.specClassName)
                    .build()
            )
            .build()
    }
}
