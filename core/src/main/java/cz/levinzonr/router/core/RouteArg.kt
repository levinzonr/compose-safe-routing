package cz.levinzonr.router.core


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

