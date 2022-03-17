package cz.levinzonr.saferoute.core

import cz.levinzonr.saferoute.core.router.Direction

interface NavGraphSpec: Direction {
    val name: String
    val start: RouteSpec<*>

    override val route: String get() = name
}