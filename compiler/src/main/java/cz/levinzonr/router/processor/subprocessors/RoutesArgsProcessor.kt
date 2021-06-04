package cz.levinzonr.router.processor.subprocessors

import com.squareup.kotlinpoet.FileSpec
import cz.levinzonr.router.processor.Constants
import cz.levinzonr.router.processor.codegen.RouteArgsBuilder
import cz.levinzonr.router.processor.models.ModelData
import java.io.File

object RoutesArgsProcessor : FileGenProcessor {

    override fun process(data: ModelData, destinationDir: File) {
        try {
            val packageName = data.packageName + "." + Constants.FILE_ARGS_DIR
            data.routes.filter { it.arguments.isNotEmpty() }.forEach {
                val spec = RouteArgsBuilder(packageName, it).build()
                FileSpec.builder(packageName, spec.name!!)
                    .addImport(Constants.PACKAGE_NAV_COMPOSE, Constants.CLASS_NAV_ARG)
                    .addImport(Constants.PACKAGE_NAVIGATION,Constants.CLASS_NAV_TYPE)
                    .addType(spec)
                    .build()
                    .writeTo(destinationDir)

            }
        } catch (e: Exception) {
            throw IllegalStateException("Error processing args data: ${e.stackTraceToString()}")
        }
    }

}