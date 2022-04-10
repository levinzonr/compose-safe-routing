package com.levinzonr.saferoute.codegen.codegen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.NavGraphData

object NavGraphsCodegen : FilesGen {

    override fun generate(data: ModelData): List<FileSpec> {
        return data.navGraphs.map {
            FileSpec.get(data.packageName, it.createSpecProperty())
        }
    }


    private fun NavGraphData.createSpecProperty() : TypeSpec{
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