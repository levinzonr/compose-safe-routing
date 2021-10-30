package cz.levinzonr.saferoute.core

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

/**
 * An interface to fully describe the app route for the Compose Nav Component
 * @path - full route path with arguments
 * @navArgs - list of the NamedNavArguments for this route, corresponds with @path
 */
interface RouteSpec<A> {
    val route: String
    val navArgs: List<NamedNavArgument>
    val argsFactory: RouteArgsFactory<A>
    val deepLinks: List<NavDeepLink>
    val content: ComposableFun?
}