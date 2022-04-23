package com.levinzonr.saferoute.codegen.codegen

import com.squareup.kotlinpoet.*
import com.levinzonr.saferoute.codegen.codegen.extensions.createActionFun
import com.levinzonr.saferoute.codegen.codegen.extensions.deprecate
import com.levinzonr.saferoute.codegen.codegen.extensions.toParamSpec
import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.constants.KDoc
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.core.GeneratorUnit
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.RouteData


object RoutesActionsCodegen : FilesGen {

    override fun generate(data: ModelData): List<GeneratorUnit> {
        val fileSpec = FileSpec.get(
            data.packageName, TypeSpec.objectBuilder(Constants.FILE_ACTIONS)
                .addKdoc(KDoc.ROUTES_ACTIONS)
                .addActions(data.routes)
                .build()
        )

        return listOf(
            GeneratorUnit(
                fileSpec = fileSpec,
                sources = data.sources
            )
        )
    }

    private fun TypeSpec.Builder.addActions(data: List<RouteData>): TypeSpec.Builder {
        data.forEach {
            addFunction(it.toFunSpec())
        }
        return this
    }


    private fun RouteData.toFunSpec(): FunSpec {
        val builder = createActionFun("${Constants.ACTIONS_PREFIX}${name.capitalize()}")
        arguments.forEach { builder.addParameter(it.toParamSpec()) }
        return builder
            .addKdoc(KDoc.ROUTES_ACTIONS_FUN, name)
            .deprecate(
                message = "RouteActions.kt is deprecated, use Routes objects instead",
                replaceWithExpression = "${specName}()",
                imports = arrayOf(specClassName)
            )
            .build()
    }
}
