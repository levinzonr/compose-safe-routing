package cz.levinzonr.router.core


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Route(val name: String, val arguments: Array<RouteArg> = [])