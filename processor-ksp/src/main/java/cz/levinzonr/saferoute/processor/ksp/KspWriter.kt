package cz.levinzonr.saferoute.processor.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSFile
import com.levinzonr.saferoute.codegen.core.Source
import com.levinzonr.saferoute.codegen.core.Writer
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.writeTo
import java.io.File

@OptIn(KotlinPoetKspPreview::class)
internal class KspWriter(
    private val codeGenerator: CodeGenerator,
    private val resolver: Resolver
) : Writer {
    override fun write(
        fileSpec: FileSpec,
        directory: File,
        sources: List<Source>
    ) {
        val dependencies = if (sources.isEmpty()) Dependencies.ALL_FILES else {
            Dependencies(
                aggregating = true,
                sources = sources.toKspFiles().toTypedArray()
            )
        }
        fileSpec.writeTo(codeGenerator, dependencies)
    }

    private fun List<Source>.toKspFiles(): List<KSFile> {
        return mapNotNull { source ->
            resolver.getAllFiles().find { ksFile ->
                ksFile.filePath == source.filepath &&
                        ksFile.fileName == source.filename &&
                        ksFile.packageName.asString() == source.packageName
            }
        }
    }
}