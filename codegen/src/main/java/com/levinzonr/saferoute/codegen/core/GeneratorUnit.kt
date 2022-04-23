package com.levinzonr.saferoute.codegen.core

import com.squareup.kotlinpoet.FileSpec

data class GeneratorUnit(
    val fileSpec: FileSpec,
    val sources: List<Source>
)