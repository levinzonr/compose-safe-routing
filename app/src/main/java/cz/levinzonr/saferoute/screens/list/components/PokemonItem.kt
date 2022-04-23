package cz.levinzonr.saferoute.screens.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.levinzonr.saferoute.data.Pokemon
import cz.levinzonr.saferoute.data.color
import cz.levinzonr.saferoute.data.pokemons

@Composable
fun PokemonItem(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    Card(
        backgroundColor = colorResource(id = pokemon.color()),
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Image(
                painter = painterResource(id = pokemon.image),
                contentDescription = "poke",
                modifier = Modifier.fillMaxHeight()
            )

            Text(
                text = pokemon.name.toString(),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.CenterVertically),
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun PreviewPokemonItem() {
    MaterialTheme {
        PokemonItem(pokemon = pokemons.random())
    }
}
