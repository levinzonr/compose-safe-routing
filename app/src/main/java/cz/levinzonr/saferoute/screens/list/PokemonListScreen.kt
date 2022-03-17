package cz.levinzonr.saferoute.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.levinzonr.saferoute.core.annotations.Route
import cz.levinzonr.saferoute.core.annotations.RouteNavGraph
import cz.levinzonr.saferoute.data.Pokemon
import cz.levinzonr.saferoute.data.pokemons
import cz.levinzonr.saferoute.screens.list.components.PokemonItem
import cz.levinzonr.saferoute.transitions.FadeInFadeOutTransition


@Composable
@Route(
    name = "PokemonList",
    transition = FadeInFadeOutTransition::class,
    navGraph = RouteNavGraph("pokedex", start = true)
)
fun PokemonListScreen(
    onPokemonClick: (Pokemon) -> Unit,
) {

    Scaffold {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(pokemons) {
                PokemonItem(
                    pokemon = it,
                    modifier = Modifier.clickable { onPokemonClick.invoke(it) }
                )
            }
        }
    }



}