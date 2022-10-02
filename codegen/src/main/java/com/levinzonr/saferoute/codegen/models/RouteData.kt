package com.levinzonr.saferoute.codegen.models

import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.core.Source
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName

data class RouteData(
    val name: String,
    val arguments: List<ArgumentData>,
    val packageName: String,
    val deeplinks: List<DeeplinkData>,
    val routeTransitionType: Any?,
    private val routeTransitionClassName: String?,
    val contentName: String,
    val params: List<String>,
    val source: Source
) {

    val routeTransitionClass
        get() = routeTransitionClassName?.let {
            ClassName(
                packageName = it.split(".").dropLast(1).joinToString("."),
                simpleNames = listOf(it.split(".").last())
            )
        }
    val argsPackageName = packageName + "." + Constants.FILE_ARGS_DIR
    val specName: String get() = "${name.capitalize()}Direction"
    val specClassName = ClassName(packageName, specName)

    val argumentsName: String get() = "${name.capitalize()}${Constants.FILE_ARGS_POSTFIX}"
    val argumentsClassName = ClassName(argsPackageName, argumentsName)

    val argsFactoryClassName = if (arguments.isEmpty())
        ClassNames.EmptyArgsFactory
    else
        ClassName(argsPackageName, getArgsFactoryName())

    val argsTypeClassName = if (arguments.isEmpty()) ClassName("kotlin", "Unit") else ClassName(
        argsPackageName,
        argumentsName
    )

    val contentClassName = ClassName(packageName, contentName)

    val routeClassName = ClassName(
        packageName, "${name.capitalize()}Direction"
    )

    val routeSpecClassName: TypeName
        get() {
            return ClassNames.RouteSpec.parameterizedBy(argsTypeClassName)
        }

    val argumentsConstructor: String get() = "$argumentsName(${arguments.joinToString { it.name }})"

    val builderName: String = "${name.decapitalize()}"

    fun getArgsFactoryName(): String {
        return "$argumentsName${Constants.FILE_ROUTE_ARG_FACTORY}"
    }
}
