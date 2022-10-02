package com.levinzonr.saferoute.codegen.models

import com.levinzonr.saferoute.codegen.core.Source
import com.squareup.kotlinpoet.ClassName

data class NavGraphData(
    val name: String,
    val routes: List<RouteData>,
    val start: RouteData,
    val packageName: String,
    val source: Source?,
) {
    val graphName get() = name.capitalize()
    val sources get() = (routes.map { it.source } + source).filterNotNull()

    val scopeClassName = ClassName(packageName, graphName + "Scope")
    val scopeBuilderClassName = ClassName(packageName, graphName + "ScopeBuilder")
}
