package cz.levinzonr.router.processor.codegen

import com.squareup.kotlinpoet.TypeSpec
import cz.levinzonr.router.processor.constants.Constants
import cz.levinzonr.router.processor.constants.KDoc
import cz.levinzonr.router.processor.extensions.asList

internal class RouteSpecInterfaceBuilder {
    fun build() : TypeSpec {
        return TypeSpec.interfaceBuilder(Constants.FILE_ROUTE_SPEC)
            .addKdoc(KDoc.ROUTE_SPEC_INTERFACE)
            .addProperty(Constants.ROUTE_SPEC_NAME, String::class)
            .addProperty(Constants.ROUTE_SPEC_ARGS, Constants.CLASS_NAMED_ARG.asList())
            .build()
    }
}