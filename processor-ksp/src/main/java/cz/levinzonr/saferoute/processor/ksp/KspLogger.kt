package cz.levinzonr.saferoute.processor.ksp

import com.google.devtools.ksp.processing.KSPLogger
import com.levinzonr.saferoute.codegen.core.LogLevel
import com.levinzonr.saferoute.codegen.core.Logger

internal class KspLogger(
    private val kspLogger: KSPLogger
) : Logger {
    override fun log(message: String, level: LogLevel) {
        when(level) {
            LogLevel.Error -> kspLogger.error(message)
            LogLevel.Debug -> kspLogger.info(message)
        }
    }
}