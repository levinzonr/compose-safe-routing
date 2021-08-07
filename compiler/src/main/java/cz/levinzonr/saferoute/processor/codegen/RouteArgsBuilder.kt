package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import cz.levinzonr.saferoute.processor.constants.Constants
import cz.levinzonr.saferoute.processor.constants.KDoc
import cz.levinzonr.saferoute.processor.extensions.asList
import cz.levinzonr.saferoute.processor.extensions.toNavType
import cz.levinzonr.saferoute.processor.models.ArgumentData
import cz.levinzonr.saferoute.processor.models.RouteData

internal class RouteArgsBuilder(
    private val packageName: String,
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
            .addFunction(buildNavBackStackEntryInitilizer())
            .addFunction(buildSavedStateHandleInitlizer())
            .build()
    }

    private fun buildNavBackStackEntryInitilizer() : FunSpec {
        val code = CodeBlock.builder()

        data.arguments.forEach {
            if (it.isNullable) {
                code.addStatement("val ${it.name} = args.arguments?.get%T(%S)", it.type, it.name)
            } else {
                code.addStatement("val ${it.name} = requireNotNull(args.arguments?.get%T(%S))", it.type, it.name)

            }
        }

        code.addStatement("return ${data.argumentsConstructor}")
        return FunSpec.builder("fromNavBackStackEntry")
            .returns(ClassName(packageName, data.argumentsName))
            .addParameter("args", Constants.CLASS_BACK_STACK_ENTRY)
            .addKdoc("A Helper function to obtain an instance of ${data.argumentsName} from NavBackStackEntry")
            .addCode(code.build())
            .build()
    }

    private fun buildSavedStateHandleInitlizer() : FunSpec {
        val code = CodeBlock.builder()

        data.arguments.forEach {
            if (it.isNullable) {
                code.addStatement("val ${it.name} = args.get<%T>(%S)", it.type, it.name)
            } else {
                code.addStatement("val ${it.name} = requireNotNull(args.get<%T>(%S))", it.type, it.name)
            }
        }

        code.addStatement("return ${data.argumentsConstructor}")
        return FunSpec.builder("fromSavedStatedHandle")
            .returns(ClassName(packageName, data.argumentsName))
            .addKdoc("A Helper function to obtain an instance of ${data.argumentsName} from SavedStateHandle")
            .addParameter("args", Constants.CLASS_SAVED_STATE_HANDLE)
            .addCode(code.build())
            .build()
    }


    private fun buildNavArgsProperty() :PropertySpec {
        val navArgClass = Constants.CLASS_NAMED_ARG

        val code = CodeBlock.builder()
            .addStatement("listOf(")
            .indent()

        data.arguments.forEach {
            code.addStatement("navArgument(%S) {", it.name)
            code.indent().addStatement("type = ${it.type.toNavType()} ")
            code.addStatement("nullable = ${it.isNullable}")
            it.optionalData?.let {
                code.addStatement("defaultValue = %L", it.value)
            }
            code.unindent()
            code.addStatement("},\n")

        }

        code.unindent().addStatement(")")

        return PropertySpec.builder("navArgs", navArgClass.asList())
            .initializer(code.build())
            .addKdoc("NamedNavArgs representation for ${data.argumentsName}")
            .build()

    }

    private fun TypeSpec.Builder.addArguments(args: List<ArgumentData>) : TypeSpec.Builder {
        addProperties(args.map { it.toPropertySpec() })
        return this
    }

    private fun TypeSpec.Builder.initConstructor(args: List<ArgumentData>) : TypeSpec.Builder {
        val params = args.map { it.toParameterSpec() }
        primaryConstructor(FunSpec.constructorBuilder().addParameters(params).build())
        return this
    }

    private fun ArgumentData.toPropertySpec() : PropertySpec {
        return PropertySpec.builder(name, type.asTypeName().copy(nullable = isNullable)).initializer(name).build()
    }

    private fun ArgumentData.toParameterSpec() : ParameterSpec {
        return ParameterSpec.builder(name, type.asTypeName().copy(nullable = isNullable)).build()
    }
}