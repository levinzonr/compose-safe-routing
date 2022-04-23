package cz.levinzonr.saferoute.accompanist.navigation

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import cz.levinzonr.saferoute.core.ProvideRouteSpecArgs
import cz.levinzonr.saferoute.core.RouteSpec

@ExperimentalMaterialNavigationApi
fun NavGraphBuilder.bottomSheet(
    routeSpec: RouteSpec<*>,
    content: @Composable ColumnScope.(backstackEntry: NavBackStackEntry) -> Unit
) = bottomSheet(routeSpec.route, routeSpec.navArgs, routeSpec.deepLinks) {
    ProvideRouteSpecArgs(spec = routeSpec, entry = it) {
        content.invoke(this, it)
    }
}

@ExperimentalMaterialNavigationApi
@Deprecated(
    message = "Use bottomSheet(Route) instead, args can be accessed using CompositionLocal APIs i.e LocalRouteArgs.current",
    replaceWith = ReplaceWith(
        "bottomSheet(spec) {\n " +
            "val args = spec.currentArgs\n" +
            "content()\n " +
            "}",
        "cz.levinzonr.saferoute.accompanist.navigation", "cz.levinzonr.saferoute.core.currentArgs"
    )
)
fun <A> NavGraphBuilder.bottomSheetWithArgs(
    routeSpec: RouteSpec<A>,
    content: @Composable ColumnScope.(backstackEntry: NavBackStackEntry, args: A) -> Unit
) = bottomSheet(routeSpec) {
    content.invoke(this, it, routeSpec.argsFactory.LocalArgs.current)
}
