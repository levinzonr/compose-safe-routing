package cz.levinzonr.router.processor.subprocessors

import com.squareup.kotlinpoet.FileSpec
import cz.levinzonr.router.processor.codegen.RoutesBuilder
import cz.levinzonr.router.processor.models.ModelData
import java.io.File

object RoutesProcessor : FileGenProcessor {
    override fun process(data: ModelData, destinationDir: File) {
        try {
            FileSpec.get(data.packageName, RoutesBuilder(data).build()).writeTo(destinationDir)
        } catch (e: Exception) {
            throw IllegalStateException("Error prosessing routes: ${e.stackTraceToString()}")
        }

    }
}