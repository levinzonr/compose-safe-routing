package cz.levinzonr.router.core.args


@Repeatable
@Retention(AnnotationRetention.SOURCE)
annotation class RouteStringArg(
    val name: String,
    val isOptional: Boolean = false,
    val isNullable: Boolean = true,
    val defaultValue: String = VALUE_NULL
) {
    companion object {
        const val VALUE_NULL = "@null"
    }
}

