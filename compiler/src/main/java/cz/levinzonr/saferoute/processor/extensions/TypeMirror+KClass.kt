package cz.levinzonr.saferoute.processor.extensions

import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import kotlin.reflect.KClass


internal fun TypeMirror.asKClass() : KClass<*> {
    return when(kind) {
        TypeKind.DECLARED -> Class.forName(toString()).kotlin
        TypeKind.INT -> Int::class
        TypeKind.FLOAT -> Float::class
        TypeKind.BOOLEAN -> Boolean::class
        TypeKind.LONG -> Long::class
        else -> throw IllegalArgumentException("Invalid type: ${toString()}")
    }
}