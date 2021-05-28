package cz.levinzonr.router.processor.models

data class RouteData(
    val name: String,
    val path: String,
    val arguments: List<ArgumentData>
)

data class ArgumentData(
    val name: String,
    val type: String
)