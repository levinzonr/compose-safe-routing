package cz.levinzonr.router.processor.codegen

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import cz.levinzonr.router.annotations.Route
import cz.levinzonr.router.processor.models.ModelData
import cz.levinzonr.router.processor.models.RouteData
import cz.levinzonr.router.processor.subprocessors.RoutesArgsProcessor
import java.io.File

class ArgsExtensionsBuilder(val dirName: String, val it: RouteData) {

    fun build(destinationDir: File) {

        val code = it.arguments.map {
            CodeBlock.of("val ${it.name} = requireNotNull(arguments).get%T(%S)", it.type, it.name)
        }


        FileSpec.builder(dirName, "NavBackStackEntry+${it.argumentsName}")
            .addImport("androidx.navigation", "NavBackStackEntry")
            .addFunction(
                FunSpec.builder("get${it.argumentsName}")
                    .receiver(ClassName("androidx.navigation", "NavBackStackEntry"))
                    .returns(ClassName(dirName, it.argumentsName))
                    .addCodeBlocks(code)
                    .addCode("return ${it.argumentsName.capitalize()}(${it.arguments.joinToString { "${it.name}!!" }})")
                    .build()

            )
            .build().writeTo(destinationDir)
    }

    private fun FunSpec.Builder.addCodeBlocks(list: List<CodeBlock>) : FunSpec.Builder {
        list.forEach { addCode(it); addCode("\n") }
        return this
    }
}