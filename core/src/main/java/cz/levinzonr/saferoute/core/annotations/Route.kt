package cz.levinzonr.saferoute.core.annotations

import cz.levinzonr.saferoute.core.transitions.DefaultRouteTransition
import cz.levinzonr.saferoute.core.transitions.RouteTransition
import kotlin.reflect.KClass

/**
 * This annotation is used to declare an application route/destination
 * @param name - defines the name of the route, its root path and the prefix for generated arguments, if not specified
 * the name of the annotated element will be used instead.
 * @param args - list of route arguments that will be used for this route
 * @param deepLinks - list of [RouteDeeplink] supported by this route
 * @param transition - specifies the transition that will be used for the route builder, uses [DefaultRouteTransition] by default
 * @see RouteTransition
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
@Repeatable
@MustBeDocumented
@java.lang.annotation.Repeatable(Routes::class)
annotation class Route(
    val name: String = AnnotationsDefaults.NULL,
    val args: Array<RouteArg> = [],
    val deepLinks: Array<RouteDeeplink> = [],
    val transition: KClass<out RouteTransition> = DefaultRouteTransition::class,
    val navGraph: RouteNavGraph = RouteNavGraph()
)


internal annotation class Routes(val value: Array<Route>)
