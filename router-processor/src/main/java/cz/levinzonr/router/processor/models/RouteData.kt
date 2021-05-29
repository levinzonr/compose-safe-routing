package cz.levinzonr.router.processor.models

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

}
