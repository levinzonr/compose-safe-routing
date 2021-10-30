package cz.levinzonr.saferoute.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cz.levinzonr.saferoute.components.Placeholder
import cz.levinzonr.saferoute.core.annotations.Route
import cz.levinzonr.saferoute.core.annotations.RouteNavGraph
import cz.levinzonr.saferoute.transitions.FadeInFadeOutTransition

@ExperimentalAnimationApi
@Composable
@Route(
    name = "profile",
    transition = FadeInFadeOutTransition::class,
    navGraph = RouteNavGraph(start = true)
)
fun ProfileScreen(onClick: () -> Unit) {
    Placeholder(color = Color.Red, title = "Profile", onClick = onClick)
}