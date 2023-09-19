package cz.levinzonr.saferoute.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cz.levinzonr.saferoute.screens.details.PokemonDetailsScreen
import cz.levinzonr.saferoute.screens.home.HomeScreen
import cz.levinzonr.saferoute.screens.list.PokemonListScreen
import cz.levinzonr.saferoute.ui.theme.RouterTheme

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    RouterTheme {
        HomeScreen(onShowPokedex = {}, onDeeplink = {})
    }
}

@Preview
@Composable
fun PreviewPokemonListScreen() {
    RouterTheme {
        PokemonListScreen(onPokemonClick = {})
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun PreviewPokemonDetailsScreen() {
    RouterTheme {
        PokemonDetailsScreen(onShowStatsClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPokemonSelector() {
    RouterTheme {
        PokemonSelector(onSelected = {})
    }
}
