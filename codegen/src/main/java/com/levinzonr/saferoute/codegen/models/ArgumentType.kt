package com.levinzonr.saferoute.codegen.models

import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import kotlin.reflect.KClass

enum class ArgumentType(val clazz: KClass<*>, val navType: String) {
    StringType(String::class, "StringType"),
    FloatType(Float::class, "FloatType"),
    LongType(Long::class, "LongType"),
    BooleanType(Boolean::class, "BoolType"),
    IntType(Int::class, "IntType");

    companion object {
        fun from(typeMirror: TypeMirror): ArgumentType {
            return when (typeMirror.kind) {
                TypeKind.LONG -> LongType
                TypeKind.BOOLEAN -> BooleanType
                TypeKind.FLOAT -> FloatType
                TypeKind.INT -> IntType
                TypeKind.DECLARED -> {
                    try {
                        val clazz = Class.forName(typeMirror.toString()).kotlin
                        if (clazz == String::class) return StringType else throw IllegalArgumentException(
                            "Type not supported ${toString()}"
                        )

                    } catch (e: Exception) {
                        throw IllegalArgumentException("Type not supported $e")
                    }
                }
                else -> throw IllegalArgumentException("Type not supported ${toString()}")
            }
        }
    }
}


