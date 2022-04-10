package com.levinzonr.saferoute.codegen.core

import com.levinzonr.saferoute.codegen.models.ModelData
import com.squareup.kotlinpoet.FileSpec

interface FilesGen {
    fun generate(data: ModelData): List<FileSpec>
}