package cz.levinzonr.saferoute.transitions

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import cz.levinzonr.saferoute.accompanist.navigation.RouteEnterTransition
import cz.levinzonr.saferoute.accompanist.navigation.RouteExitTransition
import cz.levinzonr.saferoute.accompanist.navigation.transitions.AnimatedRouteTransition

@OptIn(ExperimentalAnimationApi::class)
object FadeInFadeOutTransition : AnimatedRouteTransition() {
    override val enter: RouteEnterTransition = { fadeIn() }
    override val exit: RouteExitTransition = {  fadeOut() }
    override val popEnter: RouteEnterTransition = enter
    override val popExit: RouteExitTransition = exit
}
