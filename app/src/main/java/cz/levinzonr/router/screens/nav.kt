package cz.levinzonr.router.screens

import androidx.lifecycle.SavedStateHandle
import cz.levinzonr.router.annotations.Route
import cz.levinzonr.router.annotations.RouteArg


@Route(path = "profile")
object ProfileRoute

@Route(path = "settings")
object SettingsRoute

@Route("details")
data class DetailsRoute(
    @field:RouteArg("id") val id: String,
    @field:RouteArg("a") val a: Float,
    @field:RouteArg("b") val b: Boolean,
    @field:RouteArg("c") val c: Double,
    @field:RouteArg("d") val d: Long,
)
