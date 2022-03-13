package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import cz.levinzonr.saferoute.processor.codegen.extensions.addArguments
import cz.levinzonr.saferoute.processor.codegen.extensions.createRouteAction
import cz.levinzonr.saferoute.processor.codegen.extensions.initConstructor
import cz.levinzonr.saferoute.processor.codegen.extensions.toParamSpec
import cz.levinzonr.saferoute.processor.constants.ClassNames
import cz.levinzonr.saferoute.processor.constants.Constants
import cz.levinzonr.saferoute.processor.constants.KDoc
import cz.levinzonr.saferoute.processor.extensions.ComposableFunction
import cz.levinzonr.saferoute.processor.extensions.asList
import cz.levinzonr.saferoute.processor.models.ModelData
import cz.levinzonr.saferoute.processor.models.RouteData
import cz.levinzonr.saferoute.processor.pathbuilder.fullPathBuilder

internal class RoutesBuilder(val data: ModelData) {

    fun build(): TypeSpec {
        return TypeSpec.objectBuilder(Constants.FILE_ROUTES)
            .addProperties(data.routes.map { it.toRouteProperty() })
            .addKdoc(KDoc.ROUTES_SPEC)
            .build()
    }

    private fun RouteData.toRouteProperty(): PropertySpec {
        return PropertySpec.builder(name.capitalize(), routeClassName)
            .initializer("%T", routeClassName)
            .build()

    }


}