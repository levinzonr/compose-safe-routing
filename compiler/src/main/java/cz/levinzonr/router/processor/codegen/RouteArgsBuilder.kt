package cz.levinzonr.router.processor.codegen

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import cz.levinzonr.router.processor.Constants
import cz.levinzonr.router.processor.extensions.toNavType
import cz.levinzonr.router.processor.models.ArgumentData
import cz.levinzonr.router.processor.models.RouteData

class RouteArgsBuilder(
    private val packageName: String,
    private val data: RouteData
) {
    fun build() : TypeSpec {
        return TypeSpec.classBuilder(data.argumentsName)
            .initConstructor(data.arguments)
            .addArguments(data.arguments)
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
            code.addStatement("val ${it.name} = requireNotNull(args.arguments?.get%T(%S))", it.type, it.name)
        }

        code.addStatement("return ${data.argumentsConstructor}")
        return FunSpec.builder("fromNavBackStackEntry")
            .returns(ClassName(packageName, data.argumentsName))
            .addParameter("args", Constants.CLASS_BACK_STACK_ENTRY)
            .addCode(code.build())
            .build()
    }

    private fun buildSavedStateHandleInitlizer() : FunSpec {
        val code = CodeBlock.builder()

        data.arguments.forEach {
            code.addStatement("val ${it.name} = requireNotNull(args.get<%T>(%S))", it.type, it.name)
        }

        code.addStatement("return ${data.argumentsConstructor}")
        return FunSpec.builder("fromSavedStatedHandle")
            .returns(ClassName(packageName, data.argumentsName))
            .addParameter("args", Constants.CLASS_SAVED_STATE_HANDLE)
            .addCode(code.build())
            .build()
    }


    private fun buildNavArgsProperty() :PropertySpec {
        val list = ClassName("kotlin.collections", "List")
        val navArgClass = ClassName(Constants.PACKAGE_NAVIGATION + ".compose", "NamedNavArgument")

        val code = CodeBlock.builder()
            .addStatement("listOf(")
            .indent()

        data.arguments.forEach {
            code.addStatement("navArgument(%S) { type = ${it.type.toNavType()} },", it.name)
        }

        code.unindent().addStatement(")")

        return PropertySpec.builder("navArgs", list.parameterizedBy(navArgClass))
            .initializer(code.build())
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
        return PropertySpec.builder(name, type).initializer(name).build()
    }

    private fun ArgumentData.toParameterSpec() : ParameterSpec {
        return ParameterSpec.builder(name, type).build()
    }
}