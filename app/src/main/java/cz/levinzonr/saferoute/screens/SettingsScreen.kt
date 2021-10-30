package cz.levinzonr.saferoute.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cz.levinzonr.saferoute.accompanist.navigation.transitions.BottomSheetRouteTransition
import cz.levinzonr.saferoute.components.Placeholder
import cz.levinzonr.saferoute.core.annotations.Route
import cz.levinzonr.saferoute.core.annotations.RouteArg
import cz.levinzonr.saferoute.transitions.FadeInFadeOutTransition

@ExperimentalAnimationApi
@Composable
@Route(
    "settings",
    args = [RouteArg("hello", type = Float::class)],
    transition = FadeInFadeOutTransition::class
)
fun SettingsScreen() {
    Placeholder(color = Color.Blue, title = "Settings")
}