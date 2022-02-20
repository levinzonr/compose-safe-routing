package cz.levinzonr.saferoute.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

@Composable
fun SafeRouteNavHost(
    navController: NavHostController,
    spec: RouteSpec<*>,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) = androidx.navigation.compose.NavHost(
    navController = navController,
    startDestination = spec.route,
    modifier = modifier,
    route = route,
    builder = builder
)