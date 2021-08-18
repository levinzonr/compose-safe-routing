package cz.levinzonr.saferoute.core

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry

interface RouteArgsFactory<T> {
    fun fromBundle(bundle: Bundle?) : T
    fun fromSavedStateHandle(handle: SavedStateHandle?) : T
}

fun<T> RouteArgsFactory<T>.fromBackStackEntry(
    backStackEntry: NavBackStackEntry
) = fromBundle(backStackEntry.arguments)