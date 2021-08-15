package cz.levinzonr.saferoute.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cz.levinzonr.saferoute.annotations.Route
import cz.levinzonr.saferoute.annotations.RouteArg
import cz.levinzonr.saferoute.annotations.RouteArgType
import cz.levinzonr.saferoute.components.Placeholder

@Composable
@Route("settings", args = [RouteArg("hello", RouteArgType.FloatType)])
fun SettingsScreen() {
    Placeholder(color = Color.Blue, title = "Settings")
}