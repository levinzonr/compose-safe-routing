package cz.levinzonr.saferoute.processor.codegen.extensions

import com.squareup.kotlinpoet.FunSpec
import cz.levinzonr.saferoute.processor.models.RouteData
import cz.levinzonr.saferoute.processor.pathbuilder.fullPathBuilder

internal fun RouteData.createActionFun(funName: String) : FunSpec.Builder {
        val builder = FunSpec.builder(funName).returns(String::class)
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
    }