package cz.levinzonr.saferoute.core

interface RouteNavGraphSpec {
    val name: String
    val start: RouteSpec<*>
}