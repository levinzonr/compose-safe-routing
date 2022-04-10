package cz.levinzonr.saferoute.processor.subprocessors

import com.levinzonr.saferoute.codegen.models.ModelData
import java.io.File

internal interface FileGenProcessor {
    fun process(data: ModelData, destinationDir: File)
}