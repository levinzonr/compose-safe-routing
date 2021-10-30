package cz.levinzonr.saferoute.processor.models

internal data class NavGraphData(
    val name: String,
    val routes: List<RouteData>,
    val start: RouteData
)