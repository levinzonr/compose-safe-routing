package com.levinzonr.saferoute.codegen.core

import com.levinzonr.saferoute.codegen.models.ModelData

interface FilesGen {
    fun generate(data: ModelData): List<GeneratorUnit>
}
