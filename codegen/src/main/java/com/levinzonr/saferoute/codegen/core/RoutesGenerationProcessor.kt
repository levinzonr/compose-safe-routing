package com.levinzonr.saferoute.codegen.core

import com.levinzonr.saferoute.codegen.codegen.*
import com.levinzonr.saferoute.codegen.models.ModelData
import java.io.File


class FileGenProcessor(
    components: ProcessingComponents
) {

    private val logger: Logger = components.logger

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

    fun process(data: ModelData, dir: File) {
        generators.forEach { gens ->
            try {
                gens.generate(data).forEach { it.writeTo(dir) }
            } catch (e: Throwable) {
                logger.log(e.stackTraceToString(), level = LogLevel.Error)
            }

        }
    }
}

