package cz.levinzonr.router.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cz.levinzonr.router.components.Placeholder

@Composable
fun DetailsScreen(id: String) {
    Placeholder(color = Color.Green, title = "Details $id")
}