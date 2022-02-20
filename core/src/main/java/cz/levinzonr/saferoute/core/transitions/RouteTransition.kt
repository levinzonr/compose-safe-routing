package cz.levinzonr.saferoute.core.transitions

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import cz.levinzonr.saferoute.core.RouteSpec

/**
 * Represents the way the composable will transit in and out in navigation graph
 * Supported transitions
 * - Default
 * - Dialog
 *
 * If you use accompanist navigation you can also use
 * - BottomSheetRouteTransition
 * - AnimatedRouteTransition
 *
 */
interface RouteTransition {
    fun route(
        builder: NavGraphBuilder,
        spec: RouteSpec<*>,
        content: @Composable (NavBackStackEntry) -> Unit
    )
}

