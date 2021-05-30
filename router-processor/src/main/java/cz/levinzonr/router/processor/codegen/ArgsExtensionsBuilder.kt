package cz.levinzonr.router.processor.codegen

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import cz.levinzonr.router.processor.Constants
import cz.levinzonr.router.processor.extensions.addCodeBlocks
import cz.levinzonr.router.processor.models.RouteData
import java.io.File

class ArgsExtensionsBuilder(val dirName: String, val data: RouteData) {

    fun build(destinationDir: File) {
        addNavBackStackEntryExtensions(destinationDir)
        addSavedStateHandle(destinationDir)
    }


    private fun addSavedStateHandle(file: File) {
        FileSpec.builder(dirName, "${Constants.CLASSNAME_SAVED_STATE_HANDLE}+${data.argumentsName}")
            .addImport(Constants.PACKAGE_LIFECYCLE, Constants.CLASSNAME_SAVED_STATE_HANDLE)
            .addFunction(
                FunSpec.builder("get${data.argumentsName}")
                    .receiver(Constants.CLASS_SAVED_STATE_HANDLE)
                    .returns(ClassName(dirName, data.argumentsName))
                    .addCodeBlocks(
                        data.arguments.map {
                            CodeBlock.of("val ${it.name} = requireNotNull(get<%T>(%S))",  it.type, it.name)
                        }
                    )
                    .addCode("return ${data.argumentsName.capitalize()}(${data.arguments.joinToString { it.name }})")
                    .build()

            ).build().writeTo(file)
    }

    private fun addNavBackStackEntryExtensions(file: File) {

        val code = data.arguments.map {
            CodeBlock.of("val ${it.name} = requireNotNull(arguments).get%T(%S)", it.type, it.name)
        }

        FileSpec.builder(dirName, "${Constants.CLASSNAME_NAV_BACK_STACK_ENTRY}+${data.argumentsName}")
            .addImport(Constants.PACKAGE_NAVIGATION, Constants.CLASSNAME_NAV_BACK_STACK_ENTRY)
            .addFunction(
                FunSpec.builder("get${data.argumentsName}")
                    .receiver(Constants.CLASS_BACK_STACK_ENTRY)
                    .returns(ClassName(dirName, data.argumentsName))
                    .addCodeBlocks(code)
                    .addCode("return ${data.argumentsName.capitalize()}(${data.arguments.joinToString { "${it.name}!!" }})")
                    .build()

            )
            .build().writeTo(file)
    }

}