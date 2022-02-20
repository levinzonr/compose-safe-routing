package cz.levinzonr.saferoute.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.levinzonr.saferoute.data.Pokemon
import cz.levinzonr.saferoute.data.pokemons
import cz.levinzonr.saferoute.screens.details.args.PokemonDetailsRouteArgsFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = PokemonDetailsRouteArgsFactory.fromSavedStateHandle(savedStateHandle)

    val pokemon = MutableStateFlow<Pokemon?>(null)

    init {
        viewModelScope.launch {
            println(args.id)
            pokemons.find { args.id == it.id }?.let {
                println(it)
                pokemon.emit(it)
            }
        }

    }
}