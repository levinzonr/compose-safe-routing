package cz.levinzonr.router.processor.codegen

import com.squareup.kotlinpoet.*
import cz.levinzonr.router.processor.Constants
import cz.levinzonr.router.processor.models.ArgumentData
import cz.levinzonr.router.processor.models.OptionalArgData
import cz.levinzonr.router.processor.models.RouteData
import cz.levinzonr.router.processor.pathbuilder.fullPathBuilder
import cz.levinzonr.router.processor.pathbuilder.pathBuilder

internal class RoutesActionsBuilder(
    private val data: List<RouteData>
) {
    fun build() : TypeSpec {
        return TypeSpec.objectBuilder(Constants.FILE_ACTIONS)
            .addActions(data)
            .build()
    }


    private fun TypeSpec.Builder.addActions(data: List<RouteData>) : TypeSpec.Builder {
        data.forEach {
            addFunction(it.toFunSpec())
            if (it.arguments.any { it.optionalData != null } ) {
                addFunction(it.copy(arguments = it.arguments.filter { it.optionalData == null }).toFunSpec())
            }
        }
        return this
    }

    private fun RouteData.toFunSpec() : FunSpec {
        val builder = FunSpec.builder("${Constants.ACTIONS_PREFIX}${name.capitalize()}")
        arguments.forEach { builder.addParameter(it.toParamSpec()) }
        val path = fullPathBuilder(
            args = arguments,
            navBuilder = { "$${it.name}" },
            optionalBuilder = {"${it.name}=$${it.name}"}
        )
        builder.addStatement("return \"$name$path\"")
        return builder.returns(returnType = String::class).build()
    }

    private fun ArgumentData.toParamSpec() : ParameterSpec {
        val builder = ParameterSpec.builder(name, type.asTypeName().copy(isNullable))
        optionalData?.let { builder.defaultValue("%L", it.value) }
        return builder.build()
    }

}