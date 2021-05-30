package cz.levinzonr.router.processor.models

import cz.levinzonr.router.processor.Constants

data class RouteData(
    val name: String,
    val path: String,
    val arguments: List<ArgumentData>
) {

    fun buildPathWithArguments(argumentBuilder: (String) -> String = { "$${it}" }) : String {
        return if (arguments.isEmpty()) {
           path
        } else {
            "${path}${arguments.joinToString(prefix = "/", separator = "/",) { argumentBuilder.invoke(it.name) } }"
        }
    }

    val argumentsName: String get() = "${path.capitalize()}${Constants.FILE_ARGS_POSTFIX}"

    val argumentsConstructor : String get() = "$argumentsName(${arguments.joinToString { it.name }})"
}
