package cz.levinzonr.router.processor.subprocessors

import cz.levinzonr.router.processor.models.ModelData
import java.io.File

interface FileGenProcessor {
    fun process(data: ModelData, destinationDir: File)
}