package cz.levinzonr.saferoute.core.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf

val LocalRouter = staticCompositionLocalOf<Router> {
    EmptyRouter()
}
