package cz.levinzonr.saferoute.annotations

import kotlin.reflect.KClass

/**
 * Describes the argument type
 */
@Deprecated(
    message = "Use annotations from :core module to specify ArgType using KClass<*> directly\n" +
        "For example: RouteArgType.StringType -> String::class\n",
)
enum class RouteArgType(val clazz: KClass<*>) {
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
