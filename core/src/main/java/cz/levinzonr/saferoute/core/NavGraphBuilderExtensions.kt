package cz.levinzonr.saferoute.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation


fun NavGraphBuilder.composable(
    spec: RouteSpec<*>,
    content: @Composable (NavBackStackEntry) -> Unit
) = composable(spec.route, spec.navArgs, spec.deepLinks) {
    ProvideRouteSpecArg(spec = spec, entry = it) {
        content.invoke(it)
    }
}


fun NavGraphBuilder.navigation(
    route: String,
    startSpec: RouteSpec<*>,
    content: NavGraphBuilder.() -> Unit
) = navigation(route, startSpec.route, content)


fun<A> NavGraphBuilder.composableWithArgs(
    spec: RouteSpec<A>,
    content: @Composable (NavBackStackEntry, A) -> Unit
) = composable(spec) {
        content.invoke(it, spec.argsFactory.LocalArgs.current)
    }

