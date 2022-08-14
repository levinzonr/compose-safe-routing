package cz.levinzonr.saferoute.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cz.levinzonr.saferoute.core.router.LocalRouter
import cz.levinzonr.saferoute.core.router.Router
import cz.levinzonr.saferoute.core.router.RouterImpl



@Composable
fun<Scope> SafeRouteNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(navigators = emptyArray()),
    graph: NavGraphSpec<Scope>,
    builder: Scope.(Router) -> Unit
) {
    SafeRouteNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = graph.route,
        builder = { router ->
            val scope = graph.provideGraphScope(this)
            builder(scope, router)
        }
    )
}

@Composable
fun SafeRouteNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(navigators = emptyArray()),
    spec: RouteSpec<*>,
    route: String? = null,
    builder: NavGraphBuilder.(Router) -> Unit
) = SafeRouteNavHost(
    startDestination = spec.route,
    builder = builder,
    modifier = modifier,
    navController = navController,
    route = route
)

@Composable
fun SafeRouteNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(navigators = emptyArray()),
    startDestination: String,
    route: String? = null,
    builder: NavGraphBuilder.(Router) -> Unit
) {
    val router = remember(navController) { RouterImpl(navController) }
    CompositionLocalProvider(LocalRouter provides router) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier,
            route = route,
            builder = { builder(router) }
        )
    }
}


