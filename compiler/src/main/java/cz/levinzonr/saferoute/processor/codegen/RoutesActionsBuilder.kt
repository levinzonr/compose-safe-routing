package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import cz.levinzonr.saferoute.processor.codegen.extensions.createActionFun
import cz.levinzonr.saferoute.processor.codegen.extensions.toParamSpec
import cz.levinzonr.saferoute.processor.constants.Constants
import cz.levinzonr.saferoute.processor.constants.KDoc
import cz.levinzonr.saferoute.processor.models.ArgumentData
import cz.levinzonr.saferoute.processor.models.OptionalArgData
import cz.levinzonr.saferoute.processor.models.RouteData
import cz.levinzonr.saferoute.processor.pathbuilder.fullPathBuilder

internal class RoutesActionsBuilder(
    private val data: List<RouteData>
) {
    fun build() : TypeSpec {
        return TypeSpec.objectBuilder(Constants.FILE_ACTIONS)
            .addKdoc(KDoc.ROUTES_ACTIONS)
            .addActions(data)
            .build()
    }


    private fun TypeSpec.Builder.addActions(data: List<RouteData>) : TypeSpec.Builder {
        data.forEach {
            addFunction(it.toFunSpec())
        }
        return this
    }


    private fun RouteData.toFunSpec() : FunSpec {
        val builder = createActionFun("${Constants.ACTIONS_PREFIX}${name.capitalize()}")
        arguments.forEach { builder.addParameter(it.toParamSpec()) }
        return builder
            .addKdoc(KDoc.ROUTES_ACTIONS_FUN, name)
            .build()
    }
}
