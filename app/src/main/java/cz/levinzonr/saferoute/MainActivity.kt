package cz.levinzonr.saferoute

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Build
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
import androidx.core.app.NotificationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import cz.levinzonr.saferoute.screens.details.PokemonDetailsScreen
import cz.levinzonr.saferoute.screens.details.PokemonDetailsViewModel
import cz.levinzonr.saferoute.screens.details.args.LocalPokemonDetailsRouteArgs
import cz.levinzonr.saferoute.screens.home.HomeScreen
import cz.levinzonr.saferoute.screens.list.PokemonListScreen
import cz.levinzonr.saferoute.screens.statssheet.*
import cz.levinzonr.saferoute.ui.theme.RouterTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialNavigationApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RouterTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberAnimatedNavController()
                    val bottomSheetNavigator = rememberBottomSheetNavigator()
                    navController.navigatorProvider += bottomSheetNavigator

                    ModalBottomSheetLayout(bottomSheetNavigator) {
                        AnimatedNavHost(
                            navController = navController,
                            startDestination = Routes.HomeScreen.route
                        ) {

                            addHomeScreenRoute {
                                HomeScreen(
                                    onShowPokedex = { navController.navigate("pokedex") },
                                    onDeeplink = { navController.navigateToPokemonSelector() }
                                )
                            }
                            navigationPokedex(navController)
                        }
                    }
                }
            }
        }
    }

    @ExperimentalMaterialNavigationApi
    private fun NavGraphBuilder.navigationPokedex(navController: NavController) {
        navigation(Routes.PokemonList.route, "pokedex") {
            addPokemonListRoute {
                PokemonListScreen(onPokemonClick = {
                    navController.navigateToPokemonDetails(it.id)
                })
            }

            addPokemonDetailsRoute {
                println(LocalPokemonDetailsRouteArgs.current.id)
                val pokemon =
                    hiltViewModel<PokemonDetailsViewModel>().pokemon.collectAsState().value
                PokemonDetailsScreen(
                    pokemon = pokemon,
                    onShowStatsClick = {
                        navController.navigateToPokemonStats(
                            it.name ?: "", it.category, it.hp ?: 0, it.image
                        )
                    }
                )
            }

            addPokemonStatsRoute()

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
