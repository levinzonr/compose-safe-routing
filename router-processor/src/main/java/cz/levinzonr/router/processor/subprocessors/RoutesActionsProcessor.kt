package cz.levinzonr.router.processor.subprocessors

import com.squareup.kotlinpoet.FileSpec
import cz.levinzonr.router.processor.codegen.RoutesBuilder
import cz.levinzonr.router.processor.models.ModelData
import java.io.File

object RoutesActionsProcessor {

    fun process(buildDir: File, data: ModelData) {
        try {
            val spec = RoutesBuilder(data.routes).build()
            FileSpec.get(data.packageName, spec).writeTo(buildDir)
        } catch (e: Exception) {
            throw IllegalStateException("Error processing routes data: ${e.stackTraceToString()}")
        }
    }
}