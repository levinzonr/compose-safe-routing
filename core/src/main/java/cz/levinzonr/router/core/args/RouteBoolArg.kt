package cz.levinzonr.router.core.args
@Repeatable
@Retention(AnnotationRetention.SOURCE)
annotation class RouteBoolArg(
    val name: String,
    val isOptional: Boolean = false,
    val defaultValue: Boolean = false
)

