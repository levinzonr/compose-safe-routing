package com.levinzonr.saferoute.codegen.models

import java.lang.IllegalArgumentException

sealed class OptionalArgData<T>(val value: T) {
    class OptionalInt(value: Int) : OptionalArgData<Int>(value = value)
    class OptionalFloat(value: Float) : OptionalArgData<Float>(value = value)
    class OptionalBool(value: Boolean) : OptionalArgData<Boolean>(value = value)
    class OptionalLong(value: Long) : OptionalArgData<Long>(value = value)
    class OptionalString(value: String?) : OptionalArgData<String?>(value = value)

    companion object {

        fun build(
            type: ArgumentType,
            value: String,
            isNullable: Boolean,
            isOptional: Boolean,
            name: String
        ): OptionalArgData<*>? {

            if (!isOptional) return null

            when (type) {
                ArgumentType.StringType -> {
                    val stringDefault = if (isNullable) {
                        if (value == "@null") null else value
                    } else {
                        require(value != "@null") { "Argument $name is  optional and not nullabble but has @null default value" }
                        value
                    }

                    return OptionalString(stringDefault)
                }

                ArgumentType.IntType -> {
                    val intValue =
                        requireNotNull(value.toIntOrNull()) { "Provided arg value ($value) is not matching type $type" }
                    return OptionalInt(intValue)
                }
                ArgumentType.FloatType -> {
                    val floatValue =
                        requireNotNull(value.toFloatOrNull()) { "Provided arg value ($value) is not matching type $type" }
                    return OptionalFloat(floatValue)
                }
                ArgumentType.LongType -> {
                    val longValue =
                        requireNotNull(value.toLongOrNull()) { "Provided arg value ($value) is not matching type $type" }
                    return OptionalLong(longValue)
                }
                ArgumentType.BooleanType -> {
                    val boolValue =
                        requireNotNull(value.toBoolean()) { "Provided arg value ($value) is not matching type $type" }
                    return OptionalBool(boolValue)
                }
                else -> {
                    throw IllegalArgumentException("Type $type is not supported")
                }
            }
        }
    }
}
