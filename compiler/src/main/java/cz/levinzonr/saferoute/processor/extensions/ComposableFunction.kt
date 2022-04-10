package cz.levinzonr.saferoute.processor.extensions

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.asTypeName
import com.levinzonr.saferoute.codegen.constants.ClassNames

internal val ComposableFunction = LambdaTypeName.get(returnType = Unit::class.asTypeName()).copy(
    annotations = listOf(AnnotationSpec.builder(ClassNames.Composable).build())
)