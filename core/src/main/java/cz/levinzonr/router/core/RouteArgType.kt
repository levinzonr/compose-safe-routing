package cz.levinzonr.router.core

import kotlin.reflect.KClass

enum class RouteArgType(val clazz: KClass<*>){
    ArgStringNonNull(String::class),
    ArgStringNullable(String::class),
    ArgInt(Int::class),
    ArgFloat(Float::class),
    ArgLong(Long::class),
    ArgBool(Boolean::class)
}