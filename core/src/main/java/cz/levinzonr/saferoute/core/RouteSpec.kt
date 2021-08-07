package cz.levinzonr.saferoute.core

import androidx.navigation.compose.NamedNavArgument

/**
 * An interface to fully describe the app route for the Compose Nav Component
 * @path - full route path with arguments
 * @navArgs - list of the NamedNavArguments for this route, corresponds with @path
 */
interface RouteSpec {
    val route: String
    val navArgs: List<NamedNavArgument>
}