package com.levinzonr.saferoute.codegen.codegen.extensions

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.TypeSpec.*
import com.levinzonr.saferoute.codegen.models.ArgumentData
import com.levinzonr.saferoute.codegen.models.OptionalArgData


internal fun Builder.addArguments(args: List<ArgumentData>): Builder {
    addProperties(args.map { it.toPropertySpec() })
    return this
}

internal fun Builder.initConstructor(args: List<ArgumentData>): Builder {
    val params = args.map { it.toParamSpec() }
    primaryConstructor(FunSpec.constructorBuilder().addParameters(params).build())
    return this
}

internal fun ArgumentData.toPropertySpec(): PropertySpec {
    return PropertySpec.builder(name, type.clazz.asTypeName().copy(nullable = isNullable))
        .initializer(name).build()
}


internal fun ArgumentData.toParamSpec() : ParameterSpec {
    val builder = ParameterSpec.builder(name, type.clazz.asTypeName().copy(isNullable))
    if (optionalData is OptionalArgData.OptionalString) {
        optionalData.let { builder.defaultValue("%S", it.value) }
    } else {
        optionalData?.let { builder.defaultValue("%L", it.value) }
    }
    return builder.build()
}