package cz.levinzonr.saferoute.core

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry

interface RouteArgFactory<T> {
    fun fromBundle(bundle: Bundle?) : T
    fun fromSavedStateHandle(handle: SavedStateHandle?) : T
}

fun<T> RouteArgFactory<T>.fromBackStackEntry(backStackEntry: NavBackStackEntry) = fromBundle(backStackEntry.arguments)