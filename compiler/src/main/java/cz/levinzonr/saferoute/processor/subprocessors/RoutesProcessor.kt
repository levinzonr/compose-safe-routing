package cz.levinzonr.saferoute.processor.subprocessors

import com.squareup.kotlinpoet.FileSpec
import cz.levinzonr.saferoute.processor.codegen.RoutesBuilder
import cz.levinzonr.saferoute.processor.models.ModelData
import java.io.File

internal object RoutesProcessor : FileGenProcessor {
    override fun process(data: ModelData, destinationDir: File) {
        try {

            val spec = RoutesBuilder(data).build()
            val builder = FileSpec.builder(data.packageName, spec.name!!)
                .addType(spec)

            builder.build().writeTo(destinationDir)

        } catch (e: Exception) {
            throw IllegalStateException("Error prosessing routes: ${e.stackTraceToString()}")
        }

    }
}