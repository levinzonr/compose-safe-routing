package cz.levinzonr.saferoute.processor.models

import com.squareup.kotlinpoet.ClassName
import cz.levinzonr.saferoute.processor.constants.ClassNames
import cz.levinzonr.saferoute.processor.constants.Constants
import javax.lang.model.type.TypeMirror

internal data class RouteData(
    val name: String,
    val arguments: List<ArgumentData>,
    val packageName: String,
    val deeplinks: List<DeeplinkData>,
    val routeTransition: TypeMirror?,
    val contentClassName: ClassName,
    val params: List<String>,
    val navGraphName: String,
    val start: Boolean
) {

    val specName: String get() = "Routes.${name.capitalize()}"

    val argumentsName: String get() = "${name.capitalize()}${Constants.FILE_ARGS_POSTFIX}"

    val argsFactoryClassName = if (arguments.isEmpty())
        ClassNames.EmptyArgsFactory
    else
        ClassName(packageName, getArgsFactoryName())

    val argsTypeClassName = if (arguments.isEmpty()) ClassName("kotlin", "Unit") else ClassName(
        packageName,
        argumentsName
    )

    val argumentsConstructor: String get() = "$argumentsName(${arguments.joinToString { it.name }})"


    val builderName : String = "${name.decapitalize()}"


    fun getArgsFactoryName() : String{
        return "$argumentsName${Constants.FILE_ROUTE_ARG_FACTORY}"
    }


    fun getArgsProviderName() : String{
        return "$argumentsName${Constants.FILE_ROUTE_ARG_PROVIDER}"
    }
}
