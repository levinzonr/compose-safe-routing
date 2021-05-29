package cz.levinzonr.router.processor.extensions

import java.lang.IllegalArgumentException
import javax.lang.model.type.TypeMirror


fun TypeMirror.type() : String {
    return if (kind.isPrimitive) {
        when(toString()) {
            Long::class.javaPrimitiveType?.canonicalName -> Long::class.javaObjectType.canonicalName
            Int::class.javaPrimitiveType?.canonicalName -> Int::class.javaObjectType.canonicalName
            Float::class.javaPrimitiveType?.canonicalName -> Float::class.javaObjectType.canonicalName
            Double::class.javaPrimitiveType?.canonicalName -> Double::class.javaObjectType.canonicalName
            Boolean::class.javaPrimitiveType?.canonicalName -> Boolean::class.javaObjectType.canonicalName
            else -> throw IllegalArgumentException("Type: ${toString()} is not supported")
        }
    } else {
        toString()
    }
}

