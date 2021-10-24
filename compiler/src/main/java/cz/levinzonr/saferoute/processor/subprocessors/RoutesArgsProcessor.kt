package cz.levinzonr.saferoute.processor.subprocessors

import com.squareup.kotlinpoet.FileSpec
import cz.levinzonr.saferoute.processor.constants.Constants
import cz.levinzonr.saferoute.processor.codegen.RouteArgsBuilder
import cz.levinzonr.saferoute.processor.codegen.RouteArgsFactoryBuilder
import cz.levinzonr.saferoute.processor.codegen.RouteArgsProviderBuilder
import cz.levinzonr.saferoute.processor.models.ModelData
import java.io.File

internal object RoutesArgsProcessor : FileGenProcessor {

    override fun process(data: ModelData, destinationDir: File) {
        try {
            data.routes.filter { it.arguments.isNotEmpty() }.forEach {

                val spec = RouteArgsBuilder(it).build()
                FileSpec.builder(it.packageName, spec.name!!)
                    .addType(spec)
                    .build()
                    .writeTo(destinationDir)

                val factorySpec = RouteArgsFactoryBuilder(it).build()
                FileSpec.builder(it.packageName, factorySpec.name!!)
                    .addType(factorySpec)
                    .build()
                    .writeTo(destinationDir)


                RouteArgsProviderBuilder(it).build()
                    .writeTo(destinationDir)
            }
        } catch (e: Exception) {
            throw IllegalStateException("Error processing args data: ${e.stackTraceToString()}")
        }
    }

}