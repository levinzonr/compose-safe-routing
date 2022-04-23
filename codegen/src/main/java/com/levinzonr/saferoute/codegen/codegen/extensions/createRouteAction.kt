package com.levinzonr.saferoute.codegen.codegen.extensions

import com.levinzonr.saferoute.codegen.codegen.pathbuilder.fullPathBuilder
import com.levinzonr.saferoute.codegen.models.RouteData
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec

internal fun RouteData.createRouteAction(): PropertySpec {
    val path = fullPathBuilder(
        args = arguments,
        navBuilder = { "$${it.name}" },
        optionalBuilder = {
            val defaultNull = it.optionalData?.value ?: "@null"
            val path = if (it.isNullable) "{${it.name} ?: \"$defaultNull\"}" else it.name
            "${it.name}=$$path"
        }
    )
    return PropertySpec.builder("route", String::class)
        .addModifiers(KModifier.OVERRIDE)
        .initializer("\"$name$path\"")
        .build()
}
