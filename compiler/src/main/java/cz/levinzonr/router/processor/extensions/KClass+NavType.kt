package cz.levinzonr.router.processor.extensions

import com.squareup.kotlinpoet.ClassName
import kotlin.reflect.KClass


internal fun KClass<*>.toNavType() : String {
    return when {
        this == Int::class -> "NavType.IntType"
        this == String::class -> "NavType.StringType"
        this == Float::class -> "NavType.FloatType"
        this == Boolean::class -> "NavType.BoolType"
        this == Long::class -> "NavType.LongType"
        else -> throw IllegalArgumentException("${this.qualifiedName} is not supported")
    }
}