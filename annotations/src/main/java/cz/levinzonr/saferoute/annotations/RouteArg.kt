package cz.levinzonr.saferoute.annotations


/**
 * An annotation to describe the argument of the route
 * @param name - the name of the argument
 * @param type - the type of the argument
 * @param isOptional - determines if argument should be optional or not
 * @param defaultValue - String representation of the  default value of the argument, it will only be used if argument is optional
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
annotation class RouteArg(
    val name: String,
    val type: RouteArgType = RouteArgType.StringType,
    val isOptional: Boolean = false,
    val defaultValue: String = VALUE_NULL
) {
    companion object {
        const val VALUE_NULL = "@null"
    }
}

