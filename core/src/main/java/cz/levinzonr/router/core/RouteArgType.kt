package cz.levinzonr.router.core

import kotlin.reflect.KClass

/**
 * Describes the argument type
 */
enum class RouteArgType(val clazz: KClass<*>){
    /**
     * Represents non-nullable String type
     */
    StringType(String::class),

    /**
     * Represents nullable String type
     */
    StringNullableType(String::class),

    /**
     * Represents Int Type, cannot be null
     */
    IntType(Int::class),

    /**
     * Represents float Type, cannot be null
     */
    FloatType(Float::class),

    /**
     * Represents LongType, cannot be null
     */
    LongType(Long::class),

    /**
     * Represents Boolean value, cannot be null
     */
    BooleanType(Boolean::class)
}