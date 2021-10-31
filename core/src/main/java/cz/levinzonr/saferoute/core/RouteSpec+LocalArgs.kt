package cz.levinzonr.saferoute.core

import androidx.compose.runtime.Composable

/**
 * Retrieve Args of this route from the Composition Local
 * Must be called inside the Args Provider, such as NavGraphBuilder.composable(RouteSpec)
 * Alternatively you can you use the direct implementation, like LocalDetailsRouteArgs.current
 */
val <A> RouteSpec<A>.currentArgs: A @Composable get() = argsFactory.LocalArgs.current