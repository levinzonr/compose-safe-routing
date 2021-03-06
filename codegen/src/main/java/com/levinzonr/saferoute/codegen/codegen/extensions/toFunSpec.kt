package com.levinzonr.saferoute.codegen.codegen.extensions

import com.levinzonr.saferoute.codegen.codegen.pathbuilder.fullPathBuilder
import com.levinzonr.saferoute.codegen.models.RouteData
import com.squareup.kotlinpoet.FunSpec

internal fun RouteData.createActionFun(funName: String): FunSpec.Builder {
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
