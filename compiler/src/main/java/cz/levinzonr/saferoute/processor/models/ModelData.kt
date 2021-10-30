package cz.levinzonr.saferoute.processor.models

internal data class ModelData(
    val packageName: String,
    val navGraphs: List<NavGraphData>
) {
    val routes = navGraphs.map { it.routes }.flatten()
}