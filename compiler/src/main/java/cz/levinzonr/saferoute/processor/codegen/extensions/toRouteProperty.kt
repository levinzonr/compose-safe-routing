package cz.levinzonr.saferoute.processor.codegen.extensions

import com.squareup.kotlinpoet.PropertySpec
import cz.levinzonr.saferoute.processor.models.RouteData

internal fun RouteData.toRouteProperty(): PropertySpec {
    return PropertySpec.builder(name.capitalize(), routeClassName)
        .initializer("%T", routeClassName)
        .build()

}