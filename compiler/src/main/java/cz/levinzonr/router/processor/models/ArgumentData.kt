package cz.levinzonr.router.processor.models

import kotlin.reflect.KClass

internal data class ArgumentData(
    val name: String,
    val type: KClass<*>,
    val optionalData: OptionalArgData<*>? = null,
    val isNullable: Boolean = false
)