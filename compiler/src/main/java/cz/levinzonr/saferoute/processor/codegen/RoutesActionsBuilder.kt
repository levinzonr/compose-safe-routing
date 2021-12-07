package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
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
            optionalBuilder = {
                val defaultNull = it.optionalData?.value ?: "@null"
                val path = if (it.isNullable) "{${it.name} ?: \"$defaultNull\"}" else it.name
                "${it.name}=$$path"
            }
        )
        builder.addStatement("return \"$name$path\"")
        return builder
            .addKdoc(KDoc.ROUTES_ACTIONS_FUN, name)
            .returns(returnType = String::class).build()
    }
}

internal fun ArgumentData.toParamSpec() : ParameterSpec {
    val builder = ParameterSpec.builder(name, type.clazz.asTypeName().copy(isNullable))
    if (optionalData is OptionalArgData.OptionalString) {
        optionalData.let { builder.defaultValue("%S", it.value) }
    } else {
        optionalData?.let { builder.defaultValue("%L", it.value) }
    }
    return builder.build()
}