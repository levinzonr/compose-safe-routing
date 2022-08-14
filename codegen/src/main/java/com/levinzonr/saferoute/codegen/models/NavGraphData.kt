package com.levinzonr.saferoute.codegen.models

import com.squareup.kotlinpoet.ClassName

data class NavGraphData(
    val name: String,
    val routes: List<RouteData>,
    val start: RouteData,
    val packageName: String
) {
    val graphSpecName get() = name.capitalize()
    val graphName get() = name.capitalize() + "Graph"
    val sources get() = routes.map { it.source }

    val scopeClassName = ClassName(packageName, graphName + "Scope")
    val scopeBuilderClassName = ClassName(packageName, graphName + "ScopeBuilder")
}
