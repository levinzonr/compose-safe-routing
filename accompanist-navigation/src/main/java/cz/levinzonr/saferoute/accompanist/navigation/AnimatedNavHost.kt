package cz.levinzonr.saferoute.accompanist.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import cz.levinzonr.saferoute.core.RouteSpec
import cz.levinzonr.saferoute.core.router.*

@Composable
@ExperimentalAnimationApi
fun AnimatedSafeRouteNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(navigators = emptyArray()),
    startRouteSpec: RouteSpec<*>,
    contentAlignment: Alignment = Alignment.Center,
    route: String? = null,
    enterTransition: RouteEnterTransition = { fadeIn() },
    exitTransition: RouteExitTransition = { fadeOut() },
    popEnterTransition: RouteEnterTransition = enterTransition,
    popExitTransition: RouteExitTransition = exitTransition,
    builder: NavGraphBuilder.(Router) -> Unit
) {
    val router = RouterImpl(navController)
    CompositionLocalProvider(LocalRouter provides router) {
        AnimatedNavHost(
            navController = navController,
            startDestination = startRouteSpec.route,
            modifier = modifier,
            contentAlignment = contentAlignment,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            builder = { builder(router) },
            route = route
        )
    }
}