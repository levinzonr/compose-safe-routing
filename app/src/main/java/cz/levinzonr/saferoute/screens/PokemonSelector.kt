package cz.levinzonr.saferoute.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import cz.levinzonr.saferoute.core.annotations.Route
import cz.levinzonr.saferoute.data.Pokemon
import cz.levinzonr.saferoute.data.color
import cz.levinzonr.saferoute.data.pokemons
import cz.levinzonr.saferoute.transitions.CustomDialogTransition

@Composable
@Route(
    name = "PokemonSelector",
    transition = CustomDialogTransition::class
)
fun PokemonSelector(onSelected: (Pokemon) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {

            Text(text = "Choose pokemon to deeplink to", style = MaterialTheme.typography.h6)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(pokemons) {
                    Text(
                        text = "${it.id} ${it.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(2.dp, colorResource(id = it.color())),
                                shape = RoundedCornerShape(32.dp)
                            )
                            .padding(12.dp)
                            .clickable { onSelected(it) }
                    )
                }
            }
        }
    }
}
