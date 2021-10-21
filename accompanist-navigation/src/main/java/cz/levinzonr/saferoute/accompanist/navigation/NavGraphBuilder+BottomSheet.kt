package cz.levinzonr.saferoute.accompanist.navigation

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import cz.levinzonr.saferoute.core.ProvideRouteSpecArg
import cz.levinzonr.saferoute.core.RouteSpec
import cz.levinzonr.saferoute.core.fromBackStackEntry


@ExperimentalMaterialNavigationApi
fun NavGraphBuilder.bottomSheet(
    routeSpec: RouteSpec<*>,
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable ColumnScope.(backstackEntry: NavBackStackEntry) -> Unit
) = bottomSheet(routeSpec.route, routeSpec.navArgs, deepLinks) {
    ProvideRouteSpecArg(spec = routeSpec, entry = it) {
        content.invoke(this, it)
    }
}


@ExperimentalMaterialNavigationApi
fun<A> NavGraphBuilder.bottomSheetWithArgs(
    routeSpec: RouteSpec<A>,
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable ColumnScope.(backstackEntry: NavBackStackEntry, args: A) -> Unit
) = bottomSheet(routeSpec, deepLinks) {
    content.invoke(this, it, routeSpec.argsFactory.LocalArgs.current)
}
