package com.levinzonr.saferoute.codegen.codegen.extensions

import com.levinzonr.saferoute.codegen.models.RouteData
import com.squareup.kotlinpoet.PropertySpec

internal fun RouteData.toRouteProperty(): PropertySpec {
    return PropertySpec.builder(name.capitalize(), routeClassName)
        .initializer("%T", routeClassName)
        .build()
}
