package com.levinzonr.saferoute.codegen.codegen

import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.core.GeneratorUnit
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.RouteData
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

object RouteArgsFactoryCodegen : FilesGen {

    override fun generate(data: ModelData): List<GeneratorUnit> {
        return data.routes.filter { it.arguments.isNotEmpty() }.map {
            GeneratorUnit(
                FileSpec.get(it.argsPackageName, it.toArgsFactory()),
                listOf(it.source)
            )
        }
    }

    private fun RouteData.toArgsFactory(): TypeSpec {
        return TypeSpec.objectBuilder(getArgsFactoryName())
            .addSuperinterface(ClassNames.RouteArgsFactory.parameterizedBy(argsTypeClassName))
            .addFunction(buildNavBackStackEntryInitilizer())
            .addFunction(buildSavedStateHandleInitlizer())
            .addProperty(buildLocalArgsPropery())
            .build()
    }

    private fun RouteData.buildNavBackStackEntryInitilizer(): FunSpec {
        val code = CodeBlock.builder()
        arguments.forEach {
            if (it.isNullable) {
                code.addStatement("val ${it.name} = bundle?.get%T(%S)?.takeIf { it != %S }", it.type.clazz, it.name, "@null")
            } else {
                code.addStatement("val ${it.name} = requireNotNull(bundle?.get%T(%S))", it.type.clazz, it.name)
            }
        }

        code.addStatement("return $argumentsConstructor")
        return FunSpec.builder("fromBundle")
            .returns(argumentsClassName)
            .addParameter("bundle", ClassNames.Bundle.copy(nullable = true))
            .addModifiers(KModifier.OVERRIDE)
            .addKdoc("A Helper function to obtain an instance of $argumentsName from Bundle")
            .addCode(code.build())
            .build()
    }

    private fun RouteData.buildSavedStateHandleInitlizer(): FunSpec {
        val code = CodeBlock.builder()

        arguments.forEach {
            if (it.isNullable) {
                code.addStatement("val ${it.name} = handle?.get<%T>(%S)", it.type.clazz, it.name)
            } else {
                code.addStatement("val ${it.name} = requireNotNull(handle?.get<%T>(%S))", it.type.clazz, it.name)
            }
        }

        code.addStatement("return $argumentsConstructor")
        return FunSpec.builder("fromSavedStateHandle")
            .returns(argumentsClassName)
            .addKdoc("A Helper function to obtain an instance of $argumentsName from SavedStateHandle")
            .addParameter("handle", ClassNames.SavedStateHandle.copy(nullable = true))
            .addModifiers(KModifier.OVERRIDE)
            .addCode(code.build())
            .build()
    }

    private fun RouteData.buildLocalArgsPropery(): PropertySpec {
        return PropertySpec.builder("LocalArgs", ClassNames.ProvidableCompositionLocal.parameterizedBy(argsTypeClassName))
            .initializer("Local$argumentsName")
            .addModifiers(KModifier.OVERRIDE)
            .build()
    }
}
