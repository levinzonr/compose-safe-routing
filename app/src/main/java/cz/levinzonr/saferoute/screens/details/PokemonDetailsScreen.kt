package cz.levinzonr.saferoute.screens.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cz.levinzonr.saferoute.core.annotations.Route
import cz.levinzonr.saferoute.core.annotations.RouteArg
import cz.levinzonr.saferoute.core.annotations.RouteDeeplink
import cz.levinzonr.saferoute.data.Pokemon
import cz.levinzonr.saferoute.data.color

@ExperimentalAnimationApi
@Composable
@Route(
    name = "PokemonDetails",
    args = [RouteArg(name = "id", type = String::class)],
    deepLinks = [RouteDeeplink("app://deeplink/{id}")]
)
fun PokemonDetailsScreen(
    pokemon: Pokemon?,
    onShowStatsClick: (Pokemon) -> Unit,
) {
    val color = animateColorAsState(targetValue = if (pokemon != null) colorResource(id = pokemon.color()) else Color.White)
    AnimatedVisibility(visible = pokemon != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(color.value, shape = CircleShape)
                        .size(300.dp), contentAlignment = Alignment.TopCenter
                ) {
                    pokemon?.let {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(32.dp)
                        ) {
                            Text(
                                text = pokemon.name.toString(),
                                style = MaterialTheme.typography.h4,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                            Image(painter = painterResource(it.image), contentDescription = "")
                        }

                    }
                }

                Button(
                    onClick = { pokemon?.let { onShowStatsClick.invoke(it) } },
                    colors = ButtonDefaults.buttonColors(backgroundColor = color.value),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                    contentPadding = PaddingValues(16.dp),
                    shape = RoundedCornerShape(32.dp)
                ) {
                    Text(text = "Show Stats", color = Color.White)
                }
            }
        }
    }
}