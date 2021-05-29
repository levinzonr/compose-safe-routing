package cz.levinzonr.router.processor.subprocessors

import com.squareup.kotlinpoet.FileSpec
import cz.levinzonr.router.processor.Constants
import cz.levinzonr.router.processor.codegen.RouteArgsBuilder
import cz.levinzonr.router.processor.models.ModelData
import java.io.File

object RoutesArgsProcessor {

    fun process(buildDir: File, data: ModelData) {
        try {
            data.routes.filter { it.arguments.isNotEmpty() }.forEach {
                val spec = RouteArgsBuilder(it).build()
                FileSpec.get(data.packageName + "." + Constants.FILE_ARGS_DIR, spec)
                    .writeTo(buildDir)
            }
        } catch (e: Exception) {
            throw IllegalStateException("Error processing args data: e.pr")
        }

    }
}