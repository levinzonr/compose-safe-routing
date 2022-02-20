package cz.levinzonr.saferoute.accompanist.navigation.transitions

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import cz.levinzonr.saferoute.accompanist.navigation.RouteEnterTransition
import cz.levinzonr.saferoute.accompanist.navigation.RouteExitTransition
import cz.levinzonr.saferoute.accompanist.navigation.composable
import cz.levinzonr.saferoute.core.RouteSpec
import cz.levinzonr.saferoute.core.transitions.RouteTransition

@ExperimentalAnimationApi
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
        builder.composable(spec, this) {
            content(it)
        }
    }

    object Default : AnimatedRouteTransition() {
        override val enter: RouteEnterTransition = { fadeIn() }
        override val exit: RouteExitTransition = { fadeOut() }
        override val popEnter: RouteEnterTransition = enter
        override val popExit: RouteExitTransition = exit

    }
}

