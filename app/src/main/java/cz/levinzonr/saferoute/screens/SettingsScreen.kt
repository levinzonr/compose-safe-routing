package cz.levinzonr.saferoute.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cz.levinzonr.saferoute.components.Placeholder
import cz.levinzonr.saferoute.core.annotations.Route
import cz.levinzonr.saferoute.core.annotations.RouteArg

@Composable
@Route("settings", args = [RouteArg("hello", type = Float::class)])
fun SettingsScreen() {
    Placeholder(color = Color.Blue, title = "Settings")
}