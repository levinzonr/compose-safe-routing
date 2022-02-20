package cz.levinzonr.saferoute.accompanist.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import cz.levinzonr.saferoute.core.RouteSpec

@Composable
@ExperimentalAnimationApi
fun AnimatedNavHost(
    navController: NavHostController,
    startRouteSpec: RouteSpec<*>,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    route: String? = null,
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition) =
        { fadeIn(animationSpec = tween(700)) },
    exitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition =
        { fadeOut(animationSpec = tween(700)) },
    popEnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = enterTransition,
    popExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = exitTransition,
    builder: NavGraphBuilder.() -> Unit
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startRouteSpec.route,
        modifier = modifier,
        contentAlignment = contentAlignment,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        builder = builder,
        route = route
    )
}