package cz.levinzonr.router.core.args
@Repeatable
@Retention(AnnotationRetention.SOURCE)
annotation class RouteLongArg(
    val name: String,
    val isOptional: Boolean = false,
    val defaultValue: Long = -1L
)
