package com.levinzonr.saferoute.codegen.models

sealed class OptionalArgData<T>(val value: T){
    class OptionalInt(value: Int) : OptionalArgData<Int>(value = value)
    class OptionalFloat(value: Float) : OptionalArgData<Float>(value = value)
    class OptionalBool(value: Boolean) : OptionalArgData<Boolean>(value = value)
    class OptionalLong(value: Long) : OptionalArgData<Long>(value = value)
    class OptionalString(value: String?) : OptionalArgData<String?>(value = value)
}