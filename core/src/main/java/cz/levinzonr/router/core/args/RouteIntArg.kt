package cz.levinzonr.router.core.args

@Repeatable
@Retention(AnnotationRetention.SOURCE)
annotation class RouteIntArg(
    val name: String,
    val isOptional: Boolean = false,
    val defaultValue: Int = -1
)



