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
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import cz.levinzonr.saferoute.accompanist.navigation.SafeRouteAnimatedNavHost
import cz.levinzonr.saferoute.core.NavGraphSpec
import cz.levinzonr.saferoute.core.RouteSpec
import cz.levinzonr.saferoute.core.annotations.NavGraph
import cz.levinzonr.saferoute.core.navigation
import cz.levinzonr.saferoute.core.router.LocalRouter
import cz.levinzonr.saferoute.screens.Pokedex
import cz.levinzonr.saferoute.screens.PokedexScope
import cz.levinzonr.saferoute.screens.details.PokemonDetailsDirection
import cz.levinzonr.saferoute.screens.details.PokemonDetailsScreen
import cz.levinzonr.saferoute.screens.home.HomeScreen
import cz.levinzonr.saferoute.screens.home.HomeScreenDirection
import cz.levinzonr.saferoute.screens.home.homeScreen
import cz.levinzonr.saferoute.screens.list.PokemonListScreen
import cz.levinzonr.saferoute.screens.statssheet.PokemonStatsDirection
import cz.levinzonr.saferoute.screens.statssheet.PokemonStatsSheet
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
                            navController = rememberAnimatedNavController(bottomSheetNavigator),
                            graph = MainGraph
                        ) {
                            home {
                                val router = LocalRouter.current
                                HomeScreen(
                                    onDeeplink = {},
                                    onShowPokedex = { router.navigate(Pokedex) }
                                )
                            }

                            pokedex {
                                pokemonDetails {
                                    val router = LocalRouter.current
                                    PokemonDetailsScreen(
                                        onShowStatsClick = {
                                            router.navigate(
                                                PokemonStatsDirection(
                                                    it.name ?: "",
                                                    it.category,
                                                    it.hp ?: 0
                                                )
                                            )
                                        }
                                    )
                                }
                                pokemonList {
                                    val router = LocalRouter.current
                                    PokemonListScreen(onPokemonClick = {
                                        router.navigate(
                                            PokemonDetailsDirection(it.id)
                                        )
                                    })
                                }

                                pokemonStats {
                                    PokemonStatsSheet()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
class MainGraphScope(val navGraphBuilder: NavGraphBuilder) {
    fun home(homeContent: @Composable () -> Unit) {
        navGraphBuilder.homeScreen { homeContent.invoke() }
    }

    fun pokedex(pokedexContent: PokedexScope.() -> Unit) {
        navGraphBuilder.navigation(Pokedex, pokedexContent)
    }
}

object MainGraph : NavGraphSpec<MainGraphScope> {
    override val name: String = "main"
    override val start: RouteSpec<*> = HomeScreenDirection

    override fun provideGraphScope(graphBuilder: NavGraphBuilder): MainGraphScope {
        return MainGraphScope(graphBuilder)
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
