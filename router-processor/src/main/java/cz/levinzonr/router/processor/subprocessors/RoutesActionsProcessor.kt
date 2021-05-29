package cz.levinzonr.router.processor.subprocessors

import com.squareup.kotlinpoet.FileSpec
import cz.levinzonr.router.processor.codegen.RoutesActionsBuilder
import cz.levinzonr.router.processor.models.ModelData
import java.io.File

object RoutesActionsProcessor : FileGenProcessor {

    override fun process(data: ModelData, destinationDir: File) {
        try {
            val spec = RoutesActionsBuilder(data.routes).build()
            FileSpec.get(data.packageName, spec).writeTo(destinationDir)
        } catch (e: Exception) {
            throw IllegalStateException("Error processing routes data: ${e.stackTraceToString()}")
        }
    }
}