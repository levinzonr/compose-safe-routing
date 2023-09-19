package cz.levinzonr.saferoute.core.transitions

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import cz.levinzonr.saferoute.core.RouteSpec
import cz.levinzonr.saferoute.core.composable

object DefaultRouteTransition : RouteTransition {

    override fun route(
        builder: NavGraphBuilder,
        spec: RouteSpec<*>,
        content: @Composable (NavBackStackEntry) -> Unit
    ) {
        builder.composable(
            spec = spec,
            content = content
        )
    }
}
