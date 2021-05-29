package cz.levinzonr.router.processor.models

import kotlin.reflect.KClass

data class ArgumentData(
    val name: String,
    val type: KClass<*>
)