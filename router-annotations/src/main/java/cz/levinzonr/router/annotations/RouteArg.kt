package cz.levinzonr.router.annotations

import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class RouteArg(val name: String, val type: KClass<*> = String::class)
