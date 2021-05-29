package cz.levinzonr.router.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cz.levinzonr.router.components.Placeholder

@Composable
fun ProfileScreen(onClick: () -> Unit) {
    Placeholder(color = Color.Red, title = "Profile", onClick = onClick)
}