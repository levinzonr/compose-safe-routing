package cz.levinzonr.saferoute.processor.subprocessors

import com.squareup.kotlinpoet.FileSpec
import cz.levinzonr.saferoute.processor.codegen.RouteGraphCodegen
import cz.levinzonr.saferoute.processor.codegen.RoutesBuilder
import cz.levinzonr.saferoute.processor.codegen.RoutesTransitionsCodegen
import cz.levinzonr.saferoute.processor.logger.Logger
import cz.levinzonr.saferoute.processor.models.ModelData
import java.io.File
import javax.annotation.processing.ProcessingEnvironment

internal class RoutesProcessor(
    private val processingEnvironment: ProcessingEnvironment,
    private val logger: Logger
) : FileGenProcessor {
    override fun process(data: ModelData, destinationDir: File) {
        try {

            val spec = RoutesBuilder(data).build()
            val builder = FileSpec.builder(data.packageName, spec.name!!)
                .addType(spec)

            builder.build().writeTo(destinationDir)

            RoutesTransitionsCodegen(data, processingEnvironment, logger).generate(destinationDir)

            RouteGraphCodegen(data, logger).generate(destinationDir)


        } catch (e: Exception) {
            throw IllegalStateException("Error prosessing routes: ${e.stackTraceToString()}")
        }

    }
}