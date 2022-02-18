package cz.levinzonr.saferoute.core.annotations


internal  annotation class RouteNavGraph(
    val name: String = AnnotationsDefaults.DefaultGraphMain,
    val start: Boolean = false
)