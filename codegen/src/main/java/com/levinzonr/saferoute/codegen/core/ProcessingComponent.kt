package com.levinzonr.saferoute.codegen.core

import java.io.File

data class ProcessingComponent(
    val logger: Logger,
    val typeHelper: TypeHelper,
    val dataProcessor: DataProcessor,
    val directory: File,
    val writer: Writer = DefaultWriter()
)