package cz.levinzonr.saferoute.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavBackStackEntry

@Composable
fun <A> ProvideRouteSpecArgs(
    spec: RouteSpec<A>,
    entry: NavBackStackEntry,
    content: ComposableFun
) {
    val args = spec.argsFactory.fromBundle(entry.arguments)
    CompositionLocalProvider(spec.argsFactory.LocalArgs provides args) {
        content.invoke()
    }
}
