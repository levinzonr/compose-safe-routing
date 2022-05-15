package com.levinzonr.saferoute.codegen.models

data class ArgumentData(
    val name: String,
    val type: ArgumentType,
    val optionalData: OptionalArgData<*>? = null,
    val isNullable: Boolean = false
)
