package cz.levinzonr.saferoute.core.transitions

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import cz.levinzonr.saferoute.core.RouteSpec
import cz.levinzonr.saferoute.core.composable

abstract class AnimatedRouteTransition : RouteTransition {

    abstract val enter: RouteEnterTransition
    abstract val exit: RouteExitTransition
    abstract val popEnter: RouteEnterTransition
    abstract val popExit: RouteExitTransition

    override fun route(
        builder: NavGraphBuilder,
        spec: RouteSpec<*>,
        content: @Composable (NavBackStackEntry) -> Unit
    ) {
        builder.composable(
            spec,
            enterTransition = enter,
            exitTransition = exit,
            popEnterTransition = popEnter,
            popExitTransition = popExit,
            content = content
        )
    }

    object Default : AnimatedRouteTransition() {
        override val enter: RouteEnterTransition = { fadeIn() }
        override val exit: RouteExitTransition = { fadeOut() }
        override val popEnter: RouteEnterTransition = enter
        override val popExit: RouteExitTransition = exit
    }
}
