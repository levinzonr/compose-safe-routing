package cz.levinzonr.saferoute

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import cz.levinzonr.saferoute.accompanist.navigation.SafeRouteAnimatedNavHost
import cz.levinzonr.saferoute.core.navigation
import cz.levinzonr.saferoute.navigation.MainGraphRoutes
import cz.levinzonr.saferoute.navigation.PokedexGraph
import cz.levinzonr.saferoute.navigation.PokedexGraphRoutes
import cz.levinzonr.saferoute.navigation.homeScreen
import cz.levinzonr.saferoute.navigation.pokemonDetails
import cz.levinzonr.saferoute.navigation.pokemonList
import cz.levinzonr.saferoute.navigation.pokemonStats
import cz.levinzonr.saferoute.screens.details.PokemonDetailsScreen
import cz.levinzonr.saferoute.screens.details.PokemonDetailsViewModel
import cz.levinzonr.saferoute.screens.home.HomeScreen
import cz.levinzonr.saferoute.screens.list.PokemonListScreen
import cz.levinzonr.saferoute.ui.theme.RouterTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalMaterialNavigationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RouterTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val bottomSheetNavigator = rememberBottomSheetNavigator()
                    ModalBottomSheetLayout(bottomSheetNavigator) {
                        SafeRouteAnimatedNavHost(
                            startRouteSpec = MainGraphRoutes.HomeScreen,
                            navController = rememberAnimatedNavController(bottomSheetNavigator)
                        ) { router ->
                            homeScreen {
                                HomeScreen(
                                    onShowPokedex = { router.navigate(PokedexGraph) },
                                    onDeeplink = { router.navigate(MainGraphRoutes.HomeScreen()) }
                                )
                            }

                            navigation(PokedexGraph) {
                                pokemonList {
                                    PokemonListScreen(
                                        onPokemonClick = {
                                            router.navigate(
                                                PokedexGraphRoutes.PokemonDetails(
                                                    it.id
                                                )
                                            )
                                        }
                                    )
                                }

                                pokemonStats()

                                pokemonDetails {
                                    val viewModel = hiltViewModel<PokemonDetailsViewModel>()
                                    val poke = viewModel.pokemon.collectAsState().value
                                    PokemonDetailsScreen(pokemon = poke, onShowStatsClick = {
                                        router.navigate(
                                            PokedexGraphRoutes.PokemonStats(
                                                name = it.name ?: "",
                                                category = null,
                                                hp = it.hp ?: 0,
                                            )
                                        )
                                    })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RouterTheme {
        Greeting("Android")
    }
}
