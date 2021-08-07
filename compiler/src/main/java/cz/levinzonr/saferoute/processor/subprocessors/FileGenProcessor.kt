package cz.levinzonr.saferoute.processor.subprocessors

import cz.levinzonr.saferoute.processor.models.ModelData
import java.io.File

internal interface FileGenProcessor {
    fun process(data: ModelData, destinationDir: File)
}