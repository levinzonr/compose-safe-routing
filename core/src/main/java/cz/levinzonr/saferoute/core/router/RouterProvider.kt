package cz.levinzonr.saferoute.core.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

val LocalRouter = compositionLocalOf<Router> {
    EmptyRouter()
}

val currentRouter: Router @Composable get() = LocalRouter.current
