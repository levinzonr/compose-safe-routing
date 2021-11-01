package cz.levinzonr.saferoute.processor.logger

import javax.annotation.processing.ProcessingEnvironment
import javax.tools.Diagnostic

data class KaptLogger(
    val processingEnv: ProcessingEnvironment
) : Logger {

    override fun log(message: String, level: LogLevel) {
        processingEnv.messager.printMessage(
            when(level) {
                LogLevel.Debug -> Diagnostic.Kind.NOTE
                LogLevel.Error -> Diagnostic.Kind.ERROR
                LogLevel.Warning -> Diagnostic.Kind.WARNING
            }, message
        )
    }



}