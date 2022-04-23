package cz.levinzonr.saferoute.core.util

import android.os.Bundle
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.SavedStateHandle
import cz.levinzonr.saferoute.core.RouteArgsFactory

object EmptyArgsFactory : RouteArgsFactory<Unit> {
    override fun fromBundle(bundle: Bundle?) {
        // no op
    }

    override fun fromSavedStateHandle(handle: SavedStateHandle?) {
        // no op
    }

    override val LocalArgs: ProvidableCompositionLocal<Unit> = compositionLocalOf { }
}
