package cz.levinzonr.router.processor.codegen

import com.squareup.kotlinpoet.TypeSpec
import cz.levinzonr.router.processor.Constants
import cz.levinzonr.router.processor.extensions.asList

class RouteSpecInterfaceBuilder {
    fun build() : TypeSpec {
        return TypeSpec.interfaceBuilder(Constants.FILE_ROUTE_SPEC)
            .addProperty(Constants.ROUTE_SPEC_NAME, String::class)
            .addProperty(Constants.ROUTE_SPEC_ARGS, Constants.CLASS_NAMED_ARG.asList())
            .build()
    }
}