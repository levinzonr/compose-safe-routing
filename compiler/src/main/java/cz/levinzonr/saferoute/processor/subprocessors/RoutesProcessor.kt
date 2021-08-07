package cz.levinzonr.saferoute.processor.subprocessors

import com.squareup.kotlinpoet.FileSpec
import cz.levinzonr.saferoute.processor.constants.Constants
import cz.levinzonr.saferoute.processor.codegen.RoutesBuilder
import cz.levinzonr.saferoute.processor.extensions.importNavArgument
import cz.levinzonr.saferoute.processor.extensions.importNavType
import cz.levinzonr.saferoute.processor.models.ModelData
import java.io.File

internal object RoutesProcessor : FileGenProcessor {
    override fun process(data: ModelData, destinationDir: File) {
        try {

            val spec = RoutesBuilder(data).build()
            val builder = FileSpec.builder(data.packageName, spec.name!!)
                .addType(spec)
                .importNavArgument()
                .importNavType()

            data.routes.forEach {
                if (it.arguments.isNotEmpty()) {
                    builder.addImport(
                        data.packageName + "." + Constants.FILE_ARGS_DIR,
                        it.argumentsName
                    )
                }
            }

            builder.build().writeTo(destinationDir)

        } catch (e: Exception) {
            throw IllegalStateException("Error prosessing routes: ${e.stackTraceToString()}")
        }

    }
}