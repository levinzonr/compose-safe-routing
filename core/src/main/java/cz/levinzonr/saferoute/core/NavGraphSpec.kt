package cz.levinzonr.saferoute.core

import androidx.navigation.NavGraphBuilder
import cz.levinzonr.saferoute.core.router.Direction

interface NavGraphSpec<Scope> : Direction {
    val name: String
    val start: RouteSpec<*>

    override val route: String get() = name

    fun provideGraphScope(graphBuilder: NavGraphBuilder): Scope
}
