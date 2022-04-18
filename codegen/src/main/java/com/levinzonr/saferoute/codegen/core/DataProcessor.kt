package com.levinzonr.saferoute.codegen.core

import com.levinzonr.saferoute.codegen.models.ModelData

interface DataProcessor {
    fun process() : ModelData?
}