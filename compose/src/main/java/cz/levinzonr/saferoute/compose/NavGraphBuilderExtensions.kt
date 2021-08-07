package cz.levinzonr.saferoute.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


fun NavGraphBuilder.composable(
    spec: RouteSpec,
    deepLinks: List<NavDeepLink> = listOf(),
    content: @Composable (NavBackStackEntry) -> Unit
) = composable(spec.route, spec.navArgs, deepLinks, content)
