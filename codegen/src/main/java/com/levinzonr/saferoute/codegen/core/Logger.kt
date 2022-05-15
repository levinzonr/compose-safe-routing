package com.levinzonr.saferoute.codegen.core

interface Logger {
    fun log(message: String, level: LogLevel = LogLevel.Debug)
}
