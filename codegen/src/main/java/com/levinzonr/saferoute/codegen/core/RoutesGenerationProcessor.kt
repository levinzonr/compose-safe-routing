package com.levinzonr.saferoute.codegen.core

import com.levinzonr.saferoute.codegen.codegen.*
import com.levinzonr.saferoute.codegen.models.ModelData
import java.io.File


class RoutesGenerationProcessor(
    components: ProcessingComponents
) {

    private val logger: Logger = components.logger
    private val dataProcessor = components.dataProcessor
    private val directory = components.directory

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
        RoutesTransitionsCodegen(components.typeHelper)
    )

    fun process() = try {
        dataProcessor.process()?.let { data ->
            generators.forEach { gens ->
                gens.generate(data).forEach { it.writeTo(directory) }
            }
        }

    } catch (e: Throwable) {
        logger.log(e.stackTraceToString(), level = LogLevel.Error)
        throw e
    }
}

