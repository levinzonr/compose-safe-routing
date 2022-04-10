package cz.levinzonr.saferoute.processor.subprocessors

import com.squareup.kotlinpoet.FileSpec
import cz.levinzonr.saferoute.processor.codegen.*
import cz.levinzonr.saferoute.processor.codegen.NavGraphRoutesCodegen
import cz.levinzonr.saferoute.processor.codegen.NavGraphsCodegen
import cz.levinzonr.saferoute.processor.codegen.RoutesBuilder
import cz.levinzonr.saferoute.processor.codegen.RoutesSpecsCodegen
import cz.levinzonr.saferoute.processor.codegen.RoutesTransitionsCodegen
import cz.levinzonr.saferoute.processor.logger.Logger
import cz.levinzonr.saferoute.processor.models.ModelData
import cz.levinzonr.saferoute.processor.typehelper.TypeHelperImpl
import java.io.File
import javax.annotation.processing.ProcessingEnvironment

internal class RoutesProcessor(
    private val processingEnvironment: ProcessingEnvironment,
    private val logger: Logger
) : FileGenProcessor {
    override fun process(data: ModelData, destinationDir: File) {
        try {

            val typeHelper = TypeHelperImpl(processingEnvironment.typeUtils)
            val spec = RoutesBuilder(data).build()
            val builder = FileSpec.builder(data.packageName, spec.name!!)
                .addType(spec)

            builder.build().writeTo(destinationDir)

            RoutesTransitionsCodegen(data, typeHelper, logger).generate(destinationDir)

            NavGraphsCodegen(data, logger).generate(destinationDir)
            RoutesSpecsCodegen(data).generate(destinationDir)
            NavGraphRoutesCodegen.generate(data, destinationDir)

        } catch (e: Exception) {
            throw IllegalStateException("Error prosessing routes: ${e.stackTraceToString()}")
        }

    }
}