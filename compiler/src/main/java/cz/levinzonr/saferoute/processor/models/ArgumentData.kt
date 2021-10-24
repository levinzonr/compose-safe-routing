package cz.levinzonr.saferoute.processor.models

internal data class ArgumentData(
    val name: String,
    val type: ArgumentType,
    val optionalData: OptionalArgData<*>? = null,
    val isNullable: Boolean = false
)
