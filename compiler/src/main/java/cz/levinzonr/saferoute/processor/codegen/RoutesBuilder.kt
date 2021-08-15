package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import cz.levinzonr.saferoute.processor.constants.ClassNames
import cz.levinzonr.saferoute.processor.constants.Constants
import cz.levinzonr.saferoute.processor.constants.KDoc
import cz.levinzonr.saferoute.processor.extensions.asList
import cz.levinzonr.saferoute.processor.models.ModelData
import cz.levinzonr.saferoute.processor.models.RouteData
import cz.levinzonr.saferoute.processor.pathbuilder.fullPathBuilder

internal class RoutesBuilder(val data: ModelData) {


    fun build(): TypeSpec {
        return TypeSpec.objectBuilder(Constants.FILE_ROUTES)
            .addPaths(data.routes)
            .addKdoc(KDoc.ROUTES_SPEC)
            .build()
    }

    private fun TypeSpec.Builder.addPaths(routes: List<RouteData>) : TypeSpec.Builder {
        routes.map {

            val implementation = TypeSpec.anonymousClassBuilder()
                .addSuperinterface(ClassNames.RouteSpec.parameterizedBy(it.argsTypeClassName))
                .addProperty(it.toNamePropertySpec())
                .addProperty(it.toArgsFactoryPropertySpec())
                .addProperty(it.toArgsPropertySpec())
                .build()

            val prop = PropertySpec.builder(it.name.capitalize(), ClassNames.RouteSpec.parameterizedBy(it.argsTypeClassName))
                .addKdoc(KDoc.ROUTE_SPEC_OBJ, it.name)
                .initializer("%L", implementation)
            addProperty(prop.build())
        }
        return this
    }

    private fun RouteData.toArgsPropertySpec() : PropertySpec {
        return PropertySpec.builder(Constants.ROUTE_SPEC_ARGS, ClassNames.NamedNavArgument.asList())
            .addModifiers(KModifier.OVERRIDE)
            .initializer( if (arguments.isEmpty()) "listOf()" else "$argumentsName.navArgs")
            .build()

    }

    private fun RouteData.toNamePropertySpec() : PropertySpec {
        val path = fullPathBuilder(
            args = arguments,
            navBuilder = { "{${it.name}}" },
            optionalBuilder = { "${it.name}={${it.name}}" }
        )
        return PropertySpec.builder(Constants.ROUTE_SPEC_NAME, type = String::class)
            .addModifiers(KModifier.OVERRIDE)
            .initializer("%S", "$name$path")
            .build()
    }


    private fun RouteData.toArgsFactoryPropertySpec() : PropertySpec {
        return PropertySpec.builder("argsFactory", type = ClassNames.RouteArgsFactory.parameterizedBy(argsTypeClassName))
            .addModifiers(KModifier.OVERRIDE)
            .initializer("%T", argsFactoryClassName)
            .build()
    }
}