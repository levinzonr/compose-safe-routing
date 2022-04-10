package com.levinzonr.saferoute.codegen.codegen

import com.levinzonr.saferoute.codegen.codegen.extensions.addArguments
import com.levinzonr.saferoute.codegen.codegen.extensions.asList
import com.levinzonr.saferoute.codegen.codegen.extensions.initConstructor
import com.squareup.kotlinpoet.*
import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.constants.KDoc
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.OptionalArgData
import com.levinzonr.saferoute.codegen.models.RouteData

object RouteArgsCodegen : FilesGen {

    override fun generate(data: ModelData): List<FileSpec> {
        return data.routes.filter { it.arguments.isNotEmpty() }.map{ route ->
            FileSpec.get(
                route.argsPackageName, TypeSpec.classBuilder(route.argumentsName)
                    .initConstructor(route.arguments)
                    .addArguments(route.arguments)
                    .addKdoc(KDoc.ROUTE_ARG, route.name)
                    .addType(buildNamedArgsCompanion(route))
                    .addModifiers(KModifier.DATA)
                    .build()
            )
        }
    }

    private fun buildNamedArgsCompanion(data: RouteData): TypeSpec {

        val code = CodeBlock.builder()
        data.arguments.forEach {
            code.addStatement("val ${it.name} = requireNotNull(arguments.get)")
        }

        return TypeSpec.companionObjectBuilder()
            .addProperty(data.buildNavArgsProperty())
            .build()
    }

    private fun RouteData.buildNavArgsProperty(): PropertySpec {

        val code = CodeBlock.builder()
            .addStatement("listOf(")
            .indent()

        arguments.forEach {
            code.addStatement("%T(%S) {", ClassNames.navArgument, it.name)
            code.indent().addStatement("type = %T.${it.type.navType}", ClassNames.NavType)
            code.addStatement("nullable = ${it.isNullable}")
            it.optionalData?.let { optional ->
                if (optional is OptionalArgData.OptionalString) {
                    code.addStatement("defaultValue = %S", optional.value)
                } else {
                    code.addStatement("defaultValue = %L", optional.value)
                }
            }
            code.unindent()
            code.addStatement("},\n")

        }

        code.unindent().addStatement(")")

        return PropertySpec.builder("navArgs", ClassNames.NamedNavArgument.asList())
            .initializer(code.build())
            .addKdoc("NamedNavArgs representation for ${argumentsName}")
            .build()

    }
}