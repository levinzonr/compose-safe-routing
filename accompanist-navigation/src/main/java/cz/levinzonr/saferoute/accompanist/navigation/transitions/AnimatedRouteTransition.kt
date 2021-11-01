package cz.levinzonr.saferoute.accompanist.navigation.transitions

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import cz.levinzonr.saferoute.accompanist.navigation.RouteEnterTransition
import cz.levinzonr.saferoute.accompanist.navigation.RouteExitTransition
import cz.levinzonr.saferoute.accompanist.navigation.composable
import cz.levinzonr.saferoute.core.ComposableFun
import cz.levinzonr.saferoute.core.RouteSpec
import cz.levinzonr.saferoute.core.transitions.RouteTransition

@ExperimentalAnimationApi
interface AnimatedRouteTransition : RouteTransition {

    val enter: RouteEnterTransition?
    val exit: RouteExitTransition?
    val popEnter: RouteEnterTransition?
    val popExit: RouteExitTransition?

    override fun route(
        builder: NavGraphBuilder,
        spec: RouteSpec<*>,
        content: (NavBackStackEntry) -> Unit
    ) {
        builder.composable(spec,this) {
            content(it)
        }
    }

    companion object {
        val Default = object : AnimatedRouteTransition {
            override val enter: RouteEnterTransition = { _, _, -> fadeIn() }
            override val exit: RouteExitTransition = {_, _, -> fadeOut() }
            override val popEnter: RouteEnterTransition? = null
            override val popExit: RouteExitTransition? = null

        }
    }
}

