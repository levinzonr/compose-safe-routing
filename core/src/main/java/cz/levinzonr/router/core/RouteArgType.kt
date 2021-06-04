package cz.levinzonr.router.core

import kotlin.reflect.KClass

enum class RouteArgType(val clazz: KClass<*>){
    StringType(String::class),
    StringNullableType(String::class),
    IntType(Int::class),
    FloatType(Float::class),
    LongType(Long::class),
    BooleanType(Boolean::class)
}