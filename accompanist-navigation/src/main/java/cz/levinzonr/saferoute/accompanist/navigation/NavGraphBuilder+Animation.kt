package cz.levinzonr.saferoute.accompanist.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import cz.levinzonr.saferoute.accompanist.navigation.transitions.AnimatedRouteTransition
import cz.levinzonr.saferoute.core.ProvideRouteSpecArg
import cz.levinzonr.saferoute.core.RouteSpec


@ExperimentalAnimationApi
fun NavGraphBuilder.composable(
    spec: RouteSpec<*>,
    deepLinks: List<NavDeepLink> = spec.deepLinks,
    transition: AnimatedRouteTransition,
    content: @Composable (AnimatedVisibilityScope.(NavBackStackEntry) -> Unit)
) = composable(
    spec = spec,
    deepLinks = deepLinks,
    enterTransition = transition.enter,
    exitTransition = transition.exit,
    popExitTransition = transition.popExit,
    popEnterTransition = transition.popEnter,
    content = content
)

@ExperimentalAnimationApi
fun NavGraphBuilder.composable(
    spec: RouteSpec<*>,
    deepLinks: List<NavDeepLink> = spec.deepLinks,
    enterTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition?)? = enterTransition,
    popExitTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition?)? = exitTransition,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) = composable(
    spec.route,
    spec.navArgs,
    deepLinks,
    enterTransition,
    exitTransition,
    popEnterTransition,
    popExitTransition
)  {
    ProvideRouteSpecArg(spec = spec, entry = it) {
        content.invoke(this, it)
    }
}

@ExperimentalAnimationApi
fun<A> NavGraphBuilder.composableWithArgs(
    spec: RouteSpec<A>,
    deepLinks: List<NavDeepLink> = spec.deepLinks,
    enterTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition?)? = enterTransition,
    popExitTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition?)? = exitTransition,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry, A) -> Unit
) = composable(
    spec.route,
    spec.navArgs,
    deepLinks,
    enterTransition,
    exitTransition,
    popEnterTransition,
    popExitTransition
) {
    content.invoke(this, it, spec.argsFactory.LocalArgs.current)
}

