package cz.levinzonr.saferoute.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import cz.levinzonr.saferoute.R
import cz.levinzonr.saferoute.accompanist.navigation.transitions.AnimatedRouteTransition
import cz.levinzonr.saferoute.core.annotations.Route
import cz.levinzonr.saferoute.core.annotations.RouteNavGraph

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Route(
    transition = AnimatedRouteTransition.Default::class,
    navGraph = RouteNavGraph(start = true)
)

fun HomeScreen(
    onShowPokedex: () -> Unit,
    onDeeplink: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(text = "SafeRoute Navigation Home", style = MaterialTheme.typography.h3)
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            HomeButton(title = "Pokedex", color = colorResource(id = R.color.poke_light_teal)) {
                onShowPokedex.invoke()
            }
            HomeButton(title = "Pokelink", color = colorResource(id = R.color.poke_light_red)) {
                onDeeplink.invoke()
            }
        }
    }
}

@Composable
private fun RowScope.HomeButton(
    title: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        backgroundColor = color,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .height(85.dp).weight(1f)
            .clickable { onClick.invoke() }
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 24.dp)
        ) {
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
    }
}
