package cz.levinzonr.saferoute.accompanist.navigation.transitions

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import cz.levinzonr.saferoute.accompanist.navigation.bottomSheet
import cz.levinzonr.saferoute.core.ComposableFun
import cz.levinzonr.saferoute.core.RouteSpec
import cz.levinzonr.saferoute.core.transitions.RouteTransition

@ExperimentalMaterialNavigationApi
object BottomSheetRouteTransition: RouteTransition {

    override fun route(
        builder: NavGraphBuilder,
        spec: RouteSpec<*>,
        content: (NavBackStackEntry) -> Unit
    ) {
        builder.bottomSheet(spec) { content.invoke(it) }
    }
}