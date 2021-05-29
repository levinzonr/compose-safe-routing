package cz.levinzonr.router.processor.codegen

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import cz.levinzonr.router.processor.Constants
import cz.levinzonr.router.processor.models.ArgumentData
import cz.levinzonr.router.processor.models.RouteData

class RouteArgsBuilder(
    private val data: RouteData
) {
    fun build() : TypeSpec {
        return TypeSpec.classBuilder(data.argumentsName)
            .initConstructor(data.arguments)
            .addArguments(data.arguments)
            .addModifiers(KModifier.DATA)
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