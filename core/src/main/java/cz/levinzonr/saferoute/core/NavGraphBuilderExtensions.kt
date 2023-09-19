package cz.levinzonr.saferoute.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import cz.levinzonr.saferoute.core.transitions.AnimatedRouteTransition
import cz.levinzonr.saferoute.core.transitions.RouteEnterTransition
import cz.levinzonr.saferoute.core.transitions.RouteExitTransition
import cz.levinzonr.saferoute.core.transitions.RouteTransition

fun <Scope> NavGraphBuilder.navigation(
    spec: NavGraphSpec<Scope>,
    content: Scope.() -> Unit
) = navigation(
    startDestination = spec.start.route,
    route = spec.name,
    builder = { spec.provideGraphScope(this).apply(content) }
)

fun NavGraphBuilder.route(
    spec: RouteSpec<*>,
    transition: RouteTransition,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    transition.route(this, spec) {
        content(it)
    }
}

fun NavGraphBuilder.composable(
    spec: RouteSpec<*>,
    animatedRouteTransition: AnimatedRouteTransition,
    content: @Composable (NavBackStackEntry) -> Unit,
) = composable(
    spec = spec,
    enterTransition = animatedRouteTransition.enter,
    exitTransition = animatedRouteTransition.exit,
    popExitTransition = animatedRouteTransition.popExit,
    popEnterTransition = animatedRouteTransition.popEnter,
    content = content
)

fun NavGraphBuilder.composable(
    spec: RouteSpec<*>,
    enterTransition: RouteEnterTransition? = null,
    exitTransition: RouteExitTransition? = null,
    popEnterTransition: RouteEnterTransition? = enterTransition,
    popExitTransition: RouteExitTransition? = exitTransition,
    content: @Composable (NavBackStackEntry) -> Unit,
) = composable(
    spec.route,
    spec.navArgs,
    spec.deepLinks,
    enterTransition = enterTransition,
    exitTransition = exitTransition,
    popEnterTransition = popEnterTransition,
    popExitTransition = popExitTransition
) {
    ProvideRouteSpecArgs(spec = spec, entry = it) {
        content.invoke(it)
    }
}

fun NavGraphBuilder.dialog(
    spec: RouteSpec<*>,
    properties: DialogProperties = DialogProperties(),
    content: @Composable (NavBackStackEntry) -> Unit
) = dialog(spec.route, spec.navArgs, spec.deepLinks, properties) {
    ProvideRouteSpecArgs(spec = spec, entry = it) {
        content.invoke(it)
    }
}
