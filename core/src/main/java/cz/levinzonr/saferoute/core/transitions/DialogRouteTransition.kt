package cz.levinzonr.saferoute.core.transitions

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import cz.levinzonr.saferoute.core.ComposableFun
import cz.levinzonr.saferoute.core.RouteSpec
import cz.levinzonr.saferoute.core.composable
import cz.levinzonr.saferoute.core.dialog


abstract class DialogRouteTransition : RouteTransition {

    abstract val properties: DialogProperties

    override fun route(
        builder: NavGraphBuilder,
        spec: RouteSpec<*>,
        content: @Composable (NavBackStackEntry) -> Unit
    ) {
        builder.dialog(spec, properties, content)
    }

    object Default : DialogRouteTransition() {
        override val properties = DialogProperties()
    }
}
