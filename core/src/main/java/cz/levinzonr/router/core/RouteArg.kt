package cz.levinzonr.router.core


@Retention(AnnotationRetention.SOURCE)
annotation class RouteArg(
    val name: String,
    val type: RouteArgType = RouteArgType.StringNonNull,
    val isOptional: Boolean = false
)