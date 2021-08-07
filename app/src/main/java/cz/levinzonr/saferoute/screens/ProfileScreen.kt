package cz.levinzonr.saferoute.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cz.levinzonr.saferoute.annotations.Route
import cz.levinzonr.saferoute.components.Placeholder

@Composable
@Route(name = "profile")
fun ProfileScreen(onClick: () -> Unit) {
    Placeholder(color = Color.Red, title = "Profile", onClick = onClick)
}