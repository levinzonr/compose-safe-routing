package cz.levinzonr.saferoute.processor.logger

interface Logger {
    fun log(message: String, level: LogLevel = LogLevel.Debug)
}