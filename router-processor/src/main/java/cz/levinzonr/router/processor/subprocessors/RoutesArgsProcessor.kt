package cz.levinzonr.router.processor.subprocessors

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeName
import cz.levinzonr.router.processor.Constants
import cz.levinzonr.router.processor.codegen.ArgsExtensionsBuilder
import cz.levinzonr.router.processor.codegen.RouteArgsBuilder
import cz.levinzonr.router.processor.models.ArgumentData
import cz.levinzonr.router.processor.models.ModelData
import cz.levinzonr.router.processor.models.RouteData
import java.io.File

object RoutesArgsProcessor : FileGenProcessor {

    override fun process(data: ModelData, destinationDir: File) {
        try {
            data.routes.filter { it.arguments.isNotEmpty() }.forEach {
                val spec = RouteArgsBuilder(it).build()
                val dirName = data.packageName + "." + Constants.FILE_ARGS_DIR
                FileSpec.get(dirName, spec)
                    .writeTo(destinationDir)


                ArgsExtensionsBuilder(dirName, it).build(destinationDir)

            }
        } catch (e: Exception) {
            throw IllegalStateException("Error processing args data: e.pr")
        }
    }

}