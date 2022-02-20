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
fun AnimatedSafeRouteNavHost(
    navController: NavHostController,
    startRouteSpec: RouteSpec<*>,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    route: String? = null,
    enterTransition: RouteEnterTransition = { fadeIn() },
    exitTransition: RouteExitTransition = { fadeOut() },
    popEnterTransition: RouteEnterTransition = enterTransition,
    popExitTransition: RouteExitTransition = exitTransition,
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