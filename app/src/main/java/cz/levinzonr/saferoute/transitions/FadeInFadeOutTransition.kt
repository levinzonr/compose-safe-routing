package cz.levinzonr.saferoute.transitions

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import cz.levinzonr.saferoute.accompanist.navigation.RouteEnterTransition
import cz.levinzonr.saferoute.accompanist.navigation.RouteExitTransition
import cz.levinzonr.saferoute.accompanist.navigation.transitions.AnimatedRouteTransition

@OptIn(ExperimentalAnimationApi::class)
object FadeInFadeOutTransition : AnimatedRouteTransition {
    override val enter: RouteEnterTransition = { _, _ ->
        fadeIn()
    }
    override val exit: RouteExitTransition = { _, _ ->
        fadeOut()
    }
    override val popEnter: RouteEnterTransition? = null
    override val popExit: RouteExitTransition? = null
}