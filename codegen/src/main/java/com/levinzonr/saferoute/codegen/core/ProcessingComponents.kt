package com.levinzonr.saferoute.codegen.core

import java.io.File

data class ProcessingComponents(
    val logger: Logger,
    val typeHelper: TypeHelper,
    val dataProcessor: DataProcessor,
    val directory: File
)