package cz.levinzonr.saferoute.core.annotations

import cz.levinzonr.saferoute.core.DefaultRouteBuilder
import kotlin.reflect.KClass

annotation class RouteNavGraph(
    val name: String = AnnotationsDefaults.DefaultGraphMain,
    val start: Boolean = false,
    val builder: KClass<*> = DefaultRouteBuilder::class
)