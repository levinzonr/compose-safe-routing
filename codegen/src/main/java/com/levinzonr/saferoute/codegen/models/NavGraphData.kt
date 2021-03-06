package com.levinzonr.saferoute.codegen.models

data class NavGraphData(
    val name: String,
    val routes: List<RouteData>,
    val start: RouteData
) {
    val graphSpecName get() = name.capitalize()
    val graphName get() = name.capitalize() + "Graph"
    val sources get() = routes.map { it.source }
}
