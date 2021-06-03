package cz.levinzonr.router.processor.models

import kotlin.reflect.KClass

data class ArgumentData<T>(
    val name: String,
    val type: KClass<*>,
    val optionalData: OptionalData<T>? = null
)

data class OptionalData<T>(val defaultValue: T)