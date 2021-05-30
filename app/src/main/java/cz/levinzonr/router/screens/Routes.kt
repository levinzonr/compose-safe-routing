package cz.levinzonr.router.screens

import cz.levinzonr.router.annotations.Route
import cz.levinzonr.router.annotations.RouteArg


@Route(path = "profile")
object ProfileRoute

@Route(path = "settings")
object SettingsRoute

@Route("details")
data class DetailsRoute(
    @field:RouteArg("id") val id: String,
    @field:RouteArg("number") val number: Int,
    @field:RouteArg("number2") val a: Float,
    @field:RouteArg("number3") val b: Double,
    @field:RouteArg("number4") val c: Long,
    @field:RouteArg("value") val ca: Boolean,
)