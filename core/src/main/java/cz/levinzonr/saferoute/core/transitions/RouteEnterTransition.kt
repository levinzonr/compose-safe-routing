package cz.levinzonr.saferoute.core.transitions

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry

typealias RouteEnterTransition = AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition

typealias RouteExitTransition = AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
