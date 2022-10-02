package cz.levinzonr.saferoute.core

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import cz.levinzonr.saferoute.core.transitions.RouteTransition

fun<Scope> NavGraphBuilder.navigation(
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
    content: @Composable (NavBackStackEntry) -> Unit
) = composable(spec.route, spec.navArgs, spec.deepLinks) {
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

@ExperimentalAnimationApi
@Deprecated(
    message = "Use composable(Route) instead, args can be accessed using CompositionLocal APIs i.e LocalRouteArgs.current",
    replaceWith = ReplaceWith(
        "composable(spec) {\n " +
            "val args = spec.currentArgs\n" +
            "content()\n " +
            "}",
        "cz.levinzonr.saferoute.core.currentArgs"
    )
)
fun <A> NavGraphBuilder.composableWithArgs(
    spec: RouteSpec<A>,
    content: @Composable (NavBackStackEntry, A) -> Unit
) = composable(spec) {
    content.invoke(it, spec.argsFactory.LocalArgs.current)
}
