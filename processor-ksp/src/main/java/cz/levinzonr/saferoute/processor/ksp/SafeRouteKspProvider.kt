package cz.levinzonr.saferoute.processor.ksp

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

internal class SafeRouteKspProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val logger = KspLogger(environment.logger)
        logger.log(environment.options.toString())
        return SafeRouteKspProcessor(logger, environment.codeGenerator)
    }
}
