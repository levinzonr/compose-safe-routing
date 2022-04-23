package com.levinzonr.saferoute.codegen.codegen.extensions

import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.asTypeName

internal val ComposableFunction = LambdaTypeName.get(returnType = Unit::class.asTypeName()).copy(
    annotations = listOf(AnnotationSpec.builder(ClassNames.Composable).build())
)
