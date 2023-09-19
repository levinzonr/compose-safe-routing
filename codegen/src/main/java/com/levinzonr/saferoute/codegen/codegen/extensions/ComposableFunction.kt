package com.levinzonr.saferoute.codegen.codegen.extensions

import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName

internal val ComposableFunction = buildComposableFunction()

internal fun buildComposableFunction(
    receiver: TypeName? = null,
    params: List<ParameterSpec> = emptyList()
): TypeName {
    val composableAnnotation = AnnotationSpec.builder(ClassNames.Composable).build()
    return LambdaTypeName.get(
        returnType = Unit::class.asTypeName(),
        parameters = params,
        receiver = receiver
    ).copy(
        annotations = listOf(composableAnnotation)
    )
}
