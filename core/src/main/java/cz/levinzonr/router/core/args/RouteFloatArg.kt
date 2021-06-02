package cz.levinzonr.router.core.args

@Repeatable
@Retention(AnnotationRetention.SOURCE)
annotation class RouteFloatArg(
    val name: String,
    val isOptional: Boolean = false,
    val defaultValue: Float = -1f
)