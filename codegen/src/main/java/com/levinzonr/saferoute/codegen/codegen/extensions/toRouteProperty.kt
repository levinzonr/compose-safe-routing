package com.levinzonr.saferoute.codegen.codegen.extensions

import com.squareup.kotlinpoet.PropertySpec
import com.levinzonr.saferoute.codegen.models.RouteData

internal fun RouteData.toRouteProperty(): PropertySpec {
    return PropertySpec.builder(name.capitalize(), routeClassName)
        .initializer("%T", routeClassName)
        .build()

}