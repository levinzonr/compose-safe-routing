package cz.levinzonr.saferoute.transitions

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import cz.levinzonr.saferoute.accompanist.navigation.RouteEnterTransition
import cz.levinzonr.saferoute.accompanist.navigation.RouteExitTransition
import cz.levinzonr.saferoute.accompanist.navigation.transitions.AnimatedRouteTransition

@OptIn(ExperimentalAnimationApi::class)
object FadeInFadeOutTransition : AnimatedRouteTransition() {
    override val enter: RouteEnterTransition = { expandIn() }
    override val exit: RouteExitTransition = { shrinkOut() }
    override val popEnter: RouteEnterTransition = enter
    override val popExit: RouteExitTransition = exit
}
