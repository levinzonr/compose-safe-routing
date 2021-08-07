package cz.levinzonr.saferoute.processor.extensions

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

internal fun ClassName.asList() : ParameterizedTypeName {
    val list = ClassName("kotlin.collections", "List")
    return list.parameterizedBy(this)
}