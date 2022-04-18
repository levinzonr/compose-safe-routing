package com.levinzonr.saferoute.codegen.models

import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.constants.Constants
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import javax.lang.model.type.TypeMirror

data class RouteData(
    val name: String,
    val arguments: List<ArgumentData>,
    val packageName: String,
    val deeplinks: List<DeeplinkData>,
    val routeTransition: String?,
    val contentName: String,
    val params: List<String>,
    val navGraphName: String,
    val start: Boolean
) {

    val routeTransitionClass
        get() = routeTransition?.let {
            ClassName(
                packageName = routeTransition.split(".").dropLast(1).joinToString("."),
                simpleNames = listOf(routeTransition.split(".").last())
            )
        }
    val argsPackageName = packageName + "." + Constants.FILE_ARGS_DIR
    val specName: String get() = "${name.capitalize()}Route"
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
        packageName, "${name.capitalize()}Route"
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
