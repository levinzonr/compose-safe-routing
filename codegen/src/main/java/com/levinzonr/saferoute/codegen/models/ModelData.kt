package com.levinzonr.saferoute.codegen.models

data class ModelData(
    val packageName: String,
    val navGraphs: List<NavGraphData>
) {
    val routes = navGraphs.map { it.routes }.flatten()
}