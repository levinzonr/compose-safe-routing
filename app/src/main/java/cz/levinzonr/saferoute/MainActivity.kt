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
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import cz.levinzonr.saferoute.core.dialog
import cz.levinzonr.saferoute.core.navigation
import cz.levinzonr.saferoute.data.Pokemon
import cz.levinzonr.saferoute.data.pokemons
import cz.levinzonr.saferoute.screens.PokemonSelector
import cz.levinzonr.saferoute.screens.details.PokemonDetailsScreen
import cz.levinzonr.saferoute.screens.details.PokemonDetailsViewModel
import cz.levinzonr.saferoute.screens.details.args.LocalPokemonDetailsRouteArgs
import cz.levinzonr.saferoute.screens.home.HomeScreen
import cz.levinzonr.saferoute.screens.list.PokemonListScreen
import cz.levinzonr.saferoute.screens.statssheet.*
import cz.levinzonr.saferoute.ui.theme.RouterTheme
import dagger.hilt.android.AndroidEntryPoint

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
                            startDestination = Routes.Home.route
                        ) {

                            homeRoute {
                                HomeScreen(
                                    onShowPokedex = { navController.navigateToPokemonList() },
                                    onDeeplink = { navController.navigateToPokemonSelector() }
                                )
                            }

                            pokemonDetailsRoute {
                                val args = LocalPokemonDetailsRouteArgs.current
                                PokemonDetailsScreen(
                                    pokemon = pokemons.find { it.id == args.id },
                                    onShowStatsClick = { navController.navigateToPokemonStats(
                                        it.name ?: "", it.category, it.hp ?: 0, it.image
                                    )}
                                )
                            }
                            pokemonListRoute {
                                PokemonListScreen(onPokemonClick = {
                                    navController.navigateToPokemonDetails(it.id)
                                })
                            }

                            pokemonStatsRoute()

                            pokemonSelectorRoute {
                                PokemonSelector(onSelected = {
                                    triggerNotification(it)
                                })
                            }
                        }
                    }
                }
            }
        }
    }


    private fun triggerNotification(pokemon: Pokemon) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            data = Uri.parse("app://deeplink/${pokemon.id}")
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val nm = getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(NotificationChannel("channel", "channel", NotificationManager.IMPORTANCE_HIGH))
        }
        val notif = NotificationCompat.Builder(this@MainActivity, "channel")
            .setContentTitle("Deeplink to ${pokemon.name}")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        nm.notify( 0, notif)
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