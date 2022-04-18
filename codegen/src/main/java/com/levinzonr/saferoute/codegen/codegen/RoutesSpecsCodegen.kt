package com.levinzonr.saferoute.codegen.codegen

import com.levinzonr.saferoute.codegen.codegen.extensions.*
import com.levinzonr.saferoute.codegen.codegen.extensions.asList
import com.levinzonr.saferoute.codegen.codegen.extensions.createRouteAction
import com.levinzonr.saferoute.codegen.codegen.extensions.initConstructor
import com.levinzonr.saferoute.codegen.codegen.extensions.toParamSpec
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.levinzonr.saferoute.codegen.codegen.pathbuilder.fullPathBuilder
import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.RouteData
import java.io.File

 object RoutesSpecsCodegen : FilesGen {


     override fun generate(data: ModelData): List<FileSpec> {
         return data.routes.map {
             FileSpec.get(it.packageName, it.createRouteTypeSpec())
         }
     }

    private fun RouteData.createRouteTypeSpec() : TypeSpec {
        return TypeSpec.objectBuilder(routeClassName.simpleName)
            .addSuperinterface(routeSpecClassName)
            .addProperty(toNamePropertySpec())
            .addFunction(toDirectionProperty())
            .addProperty(toArgsFactoryPropertySpec())
            .addProperty(toDeeplinksProperty())
            .addProperty(toArgsPropertySpec())
            .build()
    }

    private fun RouteData.toArgsPropertySpec(): PropertySpec {
        return PropertySpec.builder(Constants.ROUTE_SPEC_ARGS, ClassNames.NamedNavArgument.asList())
            .addModifiers(KModifier.OVERRIDE)
            .initializer(if (arguments.isEmpty()) "listOf()" else "$argumentsName.navArgs")
            .build()

    }

    private fun RouteData.toNamePropertySpec(): PropertySpec {
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


    private fun RouteData.toArgsFactoryPropertySpec(): PropertySpec {
        return PropertySpec.builder(
            "argsFactory",
            type = ClassNames.RouteArgsFactory.parameterizedBy(argsTypeClassName)
        )
            .addModifiers(KModifier.OVERRIDE)
            .initializer("%T", argsFactoryClassName)
            .build()
    }

    private fun RouteData.toDirectionProperty(): FunSpec {
        val impl =  TypeSpec.anonymousClassBuilder()
            .initConstructor(arguments)
            .addArguments(arguments)
            .addProperty(createRouteAction())
            .addSuperinterface(ClassNames.Direction)
            .build()

        return FunSpec.builder("invoke")
            .addModifiers(KModifier.OPERATOR)
            .returns(ClassNames.Direction)
            .addParameters(arguments.map { it.toParamSpec() })
            .addCode("return %L", impl)
            .build()
    }

    private fun RouteData.toDeeplinksProperty(): PropertySpec {
        val initilizer =
            if (deeplinks.isEmpty()) CodeBlock.of("emptyList()") else with(CodeBlock.builder()) {
                addStatement("listOf(")
                deeplinks.forEach {
                    beginControlFlow("%T", ClassNames.NavDeepLinkDSL)
                    addStatement("uriPattern = %S", it.uriPattern)
                    addStatement("mimeType = %S", it.mimeType)
                    addStatement("action = %S", it.action)
                    endControlFlow()
                    add(",")
                }
                addStatement(")")
                build()
            }
        return PropertySpec.builder("deepLinks", type = ClassNames.NavDeepLink.asList())
            .addModifiers(KModifier.OVERRIDE)
            .initializer(initilizer)
            .build()
    }
}