package com.levinzonr.saferoute.codegen.models

data class ModelData(
    val packageName: String,
    val navGraphs: List<NavGraphData>,
    val routesWithoutGraph: List<RouteData>
) {
    val routes = navGraphs.map { it.routes }.flatten() + routesWithoutGraph

    val sources get() = routes.map { it.source }
}
