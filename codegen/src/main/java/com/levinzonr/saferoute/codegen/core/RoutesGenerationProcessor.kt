package com.levinzonr.saferoute.codegen.core

import com.levinzonr.saferoute.codegen.codegen.NavGraphRoutesCodegen
import com.levinzonr.saferoute.codegen.codegen.NavGraphScopesCodegen
import com.levinzonr.saferoute.codegen.codegen.NavGraphsCodegen
import com.levinzonr.saferoute.codegen.codegen.RouteArgsCodegen
import com.levinzonr.saferoute.codegen.codegen.RouteArgsFactoryCodegen
import com.levinzonr.saferoute.codegen.codegen.RouteArgsProviderCodegen
import com.levinzonr.saferoute.codegen.codegen.RoutesSpecsCodegen
import com.levinzonr.saferoute.codegen.codegen.RoutesTransitionsCodegen
import kotlin.math.log

class RoutesGenerationProcessor(
    component: ProcessingComponent
) {

    private val logger: Logger = component.logger
    private val dataProcessor = component.dataProcessor
    private val directory = component.directory
    private val writer = component.writer

    private val annotationsResolver = AnnotationsResolver(component.typeHelper)

    private val generators: List<FilesGen> = listOf(
        NavGraphRoutesCodegen,
        NavGraphsCodegen(logger),
        RouteArgsCodegen,
        RouteArgsFactoryCodegen,
        RouteArgsProviderCodegen,
        RoutesSpecsCodegen,
        RoutesTransitionsCodegen(annotationsResolver),
        NavGraphScopesCodegen(annotationsResolver)
    )

    fun process() = try {
        dataProcessor.process()?.let { data ->
            val navData = data.navGraphs.joinToString("\n") { "${it.graphName}: [${it.routes.map { it.name }}]" }
            logger.log("NavData: $navData", level = LogLevel.Warning)
            logger.log("NavData: ${data.routesWithoutGraph.joinToString { it.name }}", level = LogLevel.Warning)
            generators.forEach { gens ->
                try {
                    val generationUnits = gens.generate(data)
                    generationUnits.forEach {
                        writer.write(it.fileSpec, directory, it.sources)
                    }
                } catch (e: Throwable) {
                    logger.log("Error ${e.stackTraceToString()}", level = LogLevel.Error)
                }
            }
        }
    } catch (e: Throwable) {
        logger.log(e.stackTraceToString(), level = LogLevel.Error)
        throw e
    }
}
