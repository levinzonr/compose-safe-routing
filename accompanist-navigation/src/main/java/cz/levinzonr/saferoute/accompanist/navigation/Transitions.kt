package cz.levinzonr.saferoute.accompanist.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavBackStackEntry

@OptIn(ExperimentalAnimationApi::class)
typealias RouteEnterTransition = AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition

@OptIn(ExperimentalAnimationApi::class)
typealias RouteExitTransition = AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition
