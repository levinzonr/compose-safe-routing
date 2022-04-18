package com.levinzonr.saferoute.codegen.core

import com.squareup.kotlinpoet.FileSpec
import java.io.File

interface Writer {
    fun write(fileSpec: FileSpec, directory: File)
}

class DefaultWriter : Writer {
    override fun write(fileSpec: FileSpec, directory: File) {
        fileSpec.writeTo(directory)
    }
}