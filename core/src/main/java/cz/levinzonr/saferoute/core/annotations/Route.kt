package cz.levinzonr.saferoute.core.annotations

/**
 * This annotation is used to declare an application route/destination
 * @param name - defines the name of the route, its root path and the prefix for generated arguments
 * @param args - list of route arguments that will be used for this route
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Route(val name: String, val args: Array<RouteArg> = [])
