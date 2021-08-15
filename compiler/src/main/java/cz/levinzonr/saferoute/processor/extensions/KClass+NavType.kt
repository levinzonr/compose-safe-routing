package cz.levinzonr.saferoute.processor.extensions

import kotlin.reflect.KClass


internal fun KClass<*>.toNavType() : String {
    val value = when {
        this == Int::class -> "IntType"
        this == String::class -> "StringType"
        this == Float::class -> "FloatType"
        this == Boolean::class -> "BoolType"
        this == Long::class -> "LongType"
        else -> throw IllegalArgumentException("${this.qualifiedName} is not supported")
    }

    return "%T.$value"
}