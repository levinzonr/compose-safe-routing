package cz.levinzonr.saferoute.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun Placeholder(color: Color, title: String, onClick: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize().clickable { onClick.invoke() }, contentAlignment = Alignment.Center) {
        Text(text = title)
    }
}