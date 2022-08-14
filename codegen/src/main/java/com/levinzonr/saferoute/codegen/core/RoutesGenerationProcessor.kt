package com.levinzonr.saferoute.codegen.core

import com.levinzonr.saferoute.codegen.codegen.NavControllerExtensionsCodegen
import com.levinzonr.saferoute.codegen.codegen.NavGraphRoutesCodegen
import com.levinzonr.saferoute.codegen.codegen.NavGraphScopesCodegen
import com.levinzonr.saferoute.codegen.codegen.NavGraphsCodegen
import com.levinzonr.saferoute.codegen.codegen.RouteArgsCodegen
import com.levinzonr.saferoute.codegen.codegen.RouteArgsFactoryCodegen
import com.levinzonr.saferoute.codegen.codegen.RouteArgsProviderCodegen
import com.levinzonr.saferoute.codegen.codegen.RoutesActionsCodegen
import com.levinzonr.saferoute.codegen.codegen.RoutesCodegen
import com.levinzonr.saferoute.codegen.codegen.RoutesSpecsCodegen
import com.levinzonr.saferoute.codegen.codegen.RoutesTransitionsCodegen

class RoutesGenerationProcessor(
    component: ProcessingComponent
) {

    private val logger: Logger = component.logger
    private val dataProcessor = component.dataProcessor
    private val directory = component.directory
    private val writer = component.writer

    private val annotationsResolver = AnnotationsResolver(component.typeHelper)

    private val generators: List<FilesGen> = listOf(
        NavControllerExtensionsCodegen,
        NavGraphRoutesCodegen,
        NavGraphsCodegen,
        RouteArgsCodegen,
        RouteArgsFactoryCodegen,
        RouteArgsProviderCodegen,
        RoutesActionsCodegen,
        RoutesCodegen,
        RoutesSpecsCodegen,
        RoutesTransitionsCodegen(annotationsResolver),
        NavGraphScopesCodegen(annotationsResolver)
    )

    fun process() = try {
        dataProcessor.process()?.let { data ->
            generators.forEach { gens ->
                val generationUnits = gens.generate(data)
                generationUnits.forEach {
                    writer.write(it.fileSpec, directory, it.sources)
                }
            }
        }
    } catch (e: Throwable) {
        logger.log(e.stackTraceToString(), level = LogLevel.Error)
        throw e
    }
}
