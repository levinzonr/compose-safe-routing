package cz.levinzonr.saferoute.core.annotations

import cz.levinzonr.saferoute.core.DefaultRouteTransition
import cz.levinzonr.saferoute.core.RouteTransition
import kotlin.reflect.KClass

/**
 * This annotation is used to declare an application route/destination
 * @param name - defines the name of the route, its root path and the prefix for generated arguments
 * @param args - list of route arguments that will be used for this route
 * @param deepLinks - list of [RouteDeeplink] supported by this route
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Route(
    val name: String,
    val args: Array<RouteArg> = [],
    val deepLinks: Array<RouteDeeplink> = [],
    val transition: KClass<out RouteTransition> = DefaultRouteTransition::class,
    val navGraph: RouteNavGraph = RouteNavGraph()
)