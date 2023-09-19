package cz.levinzonr.saferoute.transitions

import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import cz.levinzonr.saferoute.core.transitions.AnimatedRouteTransition
import cz.levinzonr.saferoute.core.transitions.RouteEnterTransition
import cz.levinzonr.saferoute.core.transitions.RouteExitTransition

object FadeInFadeOutTransition : AnimatedRouteTransition() {
    override val enter: RouteEnterTransition = { expandIn() }
    override val exit: RouteExitTransition = { shrinkOut() }
    override val popEnter: RouteEnterTransition = enter
    override val popExit: RouteExitTransition = exit
}
