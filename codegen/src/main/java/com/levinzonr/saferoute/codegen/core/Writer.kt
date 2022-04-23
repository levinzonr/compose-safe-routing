package com.levinzonr.saferoute.codegen.core

import com.squareup.kotlinpoet.FileSpec
import java.io.File

interface Writer {
    fun write(
        fileSpec: FileSpec,
        directory: File,
        sources: List<Source> = emptyList()
    )
}

class DefaultWriter : Writer {
    override fun write(fileSpec: FileSpec, directory: File, sources: List<Source>) {
        fileSpec.writeTo(directory)
    }
}
