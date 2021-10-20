package cz.levinzonr.saferoute.processor.subprocessors

import com.squareup.kotlinpoet.FileSpec
import cz.levinzonr.saferoute.processor.codegen.NavControllerExtensionsBuilder
import cz.levinzonr.saferoute.processor.codegen.RoutesActionsBuilder
import cz.levinzonr.saferoute.processor.models.ModelData
import java.io.File

internal object RoutesActionsProcessor : FileGenProcessor {

    override fun process(data: ModelData, destinationDir: File) {
        try {
            val spec = RoutesActionsBuilder(data.routes).build()
            FileSpec.get(data.packageName, spec).writeTo(destinationDir)

            NavControllerExtensionsBuilder(data).build().writeTo(destinationDir)

        } catch (e: Exception) {
            throw IllegalStateException("Error processing routes data: ${e.stackTraceToString()}")
        }
    }
}