package cz.levinzonr.saferoute.accompanist.navigation.transitions

import androidx.compose.animation.ExperimentalAnimationApi
import cz.levinzonr.saferoute.accompanist.navigation.RouteEnterTransition
import cz.levinzonr.saferoute.accompanist.navigation.RouteExitTransition
import cz.levinzonr.saferoute.core.RouteTransition

@ExperimentalAnimationApi
interface AnimatedRouteTransition : RouteTransition {
    val enter: RouteEnterTransition?
    val exit: RouteExitTransition?
    val popEnter: RouteEnterTransition?
    val popExit: RouteExitTransition?
}

