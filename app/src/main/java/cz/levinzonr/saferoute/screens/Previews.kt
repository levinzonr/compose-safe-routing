package cz.levinzonr.saferoute.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import cz.levinzonr.saferoute.R
import cz.levinzonr.saferoute.data.pokemons
import cz.levinzonr.saferoute.screens.details.PokemonDetailsScreen
import cz.levinzonr.saferoute.screens.home.HomeScreen
import cz.levinzonr.saferoute.screens.list.PokemonListScreen
import cz.levinzonr.saferoute.screens.statssheet.PokemonStatsSheet
import cz.levinzonr.saferoute.screens.statssheet.args.LocalPokemonStatsRouteArgs
import cz.levinzonr.saferoute.screens.statssheet.args.PokemonStatsRouteArgs
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
        PokemonDetailsScreen(onShowStatsClick = {}, pokemon = pokemons.first())
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPokemonSelector() {
    RouterTheme {
        PokemonSelector(onSelected = {})
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPokemonStats() {
    RouterTheme {
        CompositionLocalProvider(LocalPokemonStatsRouteArgs provides PokemonStatsRouteArgs(
            name = "Pokemon test",
            category = "Wotah",
            hp =120,
            imageRes = R.drawable.poke001
        )) {
            PokemonStatsSheet()
        }
    }
}