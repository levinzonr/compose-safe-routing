package cz.levinzonr.router.screens

import cz.levinzonr.router.annotations.Route
import cz.levinzonr.router.annotations.RouteArg


@Route(path = "profile")
object ProfileRoute

@Route(path = "settings")
object SettingsRoute

@Route("details")
data class DetailsRoute(
    @field:RouteArg("id") val id: String
)