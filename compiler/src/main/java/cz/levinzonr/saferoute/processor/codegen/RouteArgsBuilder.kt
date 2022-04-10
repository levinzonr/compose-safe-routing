package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.constants.KDoc
import com.levinzonr.saferoute.codegen.models.OptionalArgData
import com.levinzonr.saferoute.codegen.models.RouteData

internal class RouteArgsBuilder(
    private val data: RouteData
) {
    fun build() : TypeSpec {
        return TypeSpec.classBuilder(data.argumentsName)
            .initConstructor(data.arguments)
            .addArguments(data.arguments)
            .addKdoc(KDoc.ROUTE_ARG, data.name)
            .addType(buildNamedArgsCompanion(data))
            .addModifiers(KModifier.DATA)
            .build()
    }

    private fun buildNamedArgsCompanion(data: RouteData) : TypeSpec {

        val code = CodeBlock.builder()
        data.arguments.forEach {
            code.addStatement("val ${it.name} = requireNotNull(arguments.get)")
        }

        return TypeSpec.companionObjectBuilder()
            .addProperty(buildNavArgsProperty())
            .build()
    }

    private fun buildNavArgsProperty() :PropertySpec {

        val code = CodeBlock.builder()
            .addStatement("listOf(")
            .indent()

        data.arguments.forEach {
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
            .addKdoc("NamedNavArgs representation for ${data.argumentsName}")
            .build()

    }
}