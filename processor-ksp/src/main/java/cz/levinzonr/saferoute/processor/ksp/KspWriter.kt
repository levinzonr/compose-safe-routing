package cz.levinzonr.saferoute.processor.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.levinzonr.saferoute.codegen.core.Writer
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.writeTo
import java.io.File

@OptIn(KotlinPoetKspPreview::class)
internal class KspWriter(private val codeGenerator: CodeGenerator) : Writer {
    override fun write(fileSpec: FileSpec, directory: File) {
        fileSpec.writeTo(codeGenerator, false)
    }
}