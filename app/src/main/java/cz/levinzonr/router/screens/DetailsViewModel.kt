package cz.levinzonr.router.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import cz.levinzonr.router.screens.args.DetailsRouteArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(savedStateHandle: SavedStateHandle): ViewModel() {
    init {
        println("Args: ${DetailsRouteArgs.fromSavedStatedHandle(savedStateHandle)}")
    }
}