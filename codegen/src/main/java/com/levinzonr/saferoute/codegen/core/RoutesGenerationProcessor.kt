package com.levinzonr.saferoute.codegen.core

import com.levinzonr.saferoute.codegen.codegen.*


class RoutesGenerationProcessor(
    component: ProcessingComponent
) {

    private val logger: Logger = component.logger
    private val dataProcessor = component.dataProcessor
    private val directory = component.directory
    private val writer = component.writer

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
        RoutesTransitionsCodegen(component.typeHelper)
    )

    fun process() = try {
        dataProcessor.process()?.let { data ->
            generators.forEach { gens ->
                gens.generate(data).forEach { writer.write(it, directory) }
            }
        }

    } catch (e: Throwable) {
        logger.log(e.stackTraceToString(), level = LogLevel.Error)
        throw e
    }
}
