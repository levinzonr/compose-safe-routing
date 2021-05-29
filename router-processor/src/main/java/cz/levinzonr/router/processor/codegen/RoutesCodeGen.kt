package cz.levinzonr.router.processor.codegen

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import cz.levinzonr.router.processor.Constants
import cz.levinzonr.router.processor.models.RouteData

class RoutesBuilder(
    private val data: List<RouteData>
) {
    fun build() : TypeSpec {
        return TypeSpec.objectBuilder(Constants.FILE_ACTIONS)
            .addActions(data)
            .build()
    }


    private fun TypeSpec.Builder.addActions(data: List<RouteData>) : TypeSpec.Builder {
        data.forEach { addFunction(it.toFunSpec()) }
        return this
    }

    private fun RouteData.toFunSpec() : FunSpec {
        val builder = FunSpec.builder("${Constants.ACTIONS_PREFIX}${path.capitalize()}")
        arguments.forEach { builder.addParameter(it.name, it.type) }
        builder.addStatement("return ${buildPathWithArguments()}")
        return builder.returns(returnType = String::class).build()
    }
}