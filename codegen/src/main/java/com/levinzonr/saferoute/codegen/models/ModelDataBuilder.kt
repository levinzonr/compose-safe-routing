package com.levinzonr.saferoute.codegen.models

import com.levinzonr.saferoute.codegen.core.Source

class ModelDataBuilder {

    data class Graph(val name: String, val packageName: String?, val source: Source?)
    data class Route(val routeData: RouteData, val graph: Graph?, val start: Boolean)
    data class RouteGraph(
        val name: String,
        val start: Boolean
    )

    val graphs = mutableListOf<Graph>()
    private val routes = mutableListOf<Route>()


    fun addGraph(name: String, packageName: String, source: Source?) {
        val graph = Graph(name, packageName, source)
        graphs.add(graph)
    }

    fun addRoute(routeData: RouteData, graphName: String?, start: Boolean?) {
        val graph = graphs.find { graph -> graph.name == graphName }
        routes.add(Route(routeData, graph, start ?: false))
    }


    fun addRoute(routeData: RouteData, graphs: List<RouteGraph>) : ModelDataBuilder {
        if (graphs.isEmpty()) {
            addRoute(routeData, null, false)
        } else {
            graphs.forEach {
                addRoute(routeData, it.name, it.start)
            }
        }
        return this
    }

    fun build(packageName: String): ModelData {
        return ModelData(
            packageName = packageName,
            navGraphs = buildNavGraphData(packageName),
            routesWithoutGraph = routes.filter { it.graph == null }.map { it.routeData }
        )
    }

    private fun buildNavGraphData(packageName: String): List<NavGraphData> {
        return graphs.map { graph ->
            val routes = routes.filter { it.graph?.name == graph.name }
            val start =
                requireNotNull(routes.find { it.start }) { "NavGraph ${graph.name} doesn't have a start route" }
            NavGraphData(
                name = graph.name.replace("NavGraph", "").replace("Graph", ""),
                routes = routes.map { it.routeData },
                start = start.routeData,
                packageName = graph.packageName ?: packageName,
                source = graph.source
            )
        }
    }
}