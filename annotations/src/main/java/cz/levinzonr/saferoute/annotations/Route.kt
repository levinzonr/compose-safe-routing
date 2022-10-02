package cz.levinzonr.saferoute.annotations
/**
 * This annotation is used to declare an application route/destination
 * @param name - defines the name of the route, its root path and the prefix for generated arguments
 * @param args - list of route arguments that will be used for this route
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
@Deprecated(
    message = "Use annotations from the :core module instead",
    replaceWith = ReplaceWith("Route", "cz.levinzonr.saferoute.core.annotations.Route")
)
annotation class Route(
    val name: String,
    val args: Array<RouteArg> = [],
    val graph: String = "mainGraph",
    val start: Boolean = false,
)
