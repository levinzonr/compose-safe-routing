package cz.levinzonr.saferoute.processor.models

import cz.levinzonr.saferoute.processor.constants.Constants

internal data class RouteData(
    val name: String,
    val arguments: List<ArgumentData>
) {

    val argumentsName: String get() = "${name.capitalize()}${Constants.FILE_ARGS_POSTFIX}"

    val argumentsConstructor: String get() = "$argumentsName(${arguments.joinToString { it.name }})"
}
