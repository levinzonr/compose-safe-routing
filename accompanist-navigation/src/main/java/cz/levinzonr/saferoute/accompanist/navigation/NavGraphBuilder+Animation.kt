package cz.levinzonr.saferoute.accompanist.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import cz.levinzonr.saferoute.accompanist.navigation.transitions.AnimatedRouteTransition
import cz.levinzonr.saferoute.core.ProvideRouteSpecArgs
import cz.levinzonr.saferoute.core.RouteSpec

@ExperimentalAnimationApi
fun NavGraphBuilder.composable(
    spec: RouteSpec<*>,
    enterTransition: RouteEnterTransition = { fadeIn(animationSpec = tween(700)) },
    exitTransition: RouteExitTransition = { fadeOut(animationSpec = tween(700)) },
    popEnterTransition: RouteEnterTransition = enterTransition,
    popExitTransition: RouteExitTransition = exitTransition,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) = composable(
    spec.route,
    spec.navArgs,
    spec.deepLinks,
    enterTransition,
    exitTransition,
    popEnterTransition,
    popExitTransition
) {
    ProvideRouteSpecArgs(spec = spec, entry = it) {
        content.invoke(this, it)
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.composable(
    spec: RouteSpec<*>,
    transition: AnimatedRouteTransition,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) = composable(
    spec = spec,
    enterTransition = transition.enter,
    exitTransition = transition.exit,
    popEnterTransition = transition.popEnter,
    popExitTransition = transition.popExit,
    content = content
)

@ExperimentalAnimationApi
@Deprecated(
    message = "Use composable(Route) instead, args can be accessed using CompositionLocal APIs i.e LocalRouteArgs.current",
    replaceWith = ReplaceWith(
        "composable(spec, enterTransition, exitTransition, popEnterTransition, popExitTransition) {\n " +
            "val args = spec.currentArgs\n" +
            "content()\n " +
            " }",
        "cz.levinzonr.saferoute.accompanist.navigation", "cz.levinzonr.saferoute.core.currentArgs"
    )
)
fun <A> NavGraphBuilder.composableWithArgs(
    spec: RouteSpec<A>,
    enterTransition: RouteEnterTransition = { fadeIn() },
    exitTransition: RouteExitTransition = { fadeOut() },
    popEnterTransition: RouteEnterTransition = enterTransition,
    popExitTransition: RouteExitTransition = exitTransition,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry, A) -> Unit
) = composable(
    spec.route,
    spec.navArgs,
    spec.deepLinks,
    enterTransition,
    exitTransition,
    popEnterTransition,
    popExitTransition
) {
    content.invoke(this, it, spec.argsFactory.LocalArgs.current)
}
