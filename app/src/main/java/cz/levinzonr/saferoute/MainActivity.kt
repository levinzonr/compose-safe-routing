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
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import cz.levinzonr.saferoute.accompanist.navigation.*
import cz.levinzonr.saferoute.core.dialog
import cz.levinzonr.saferoute.core.navigation
import cz.levinzonr.saferoute.data.Pokemon
import cz.levinzonr.saferoute.screens.PokemonSelector
import cz.levinzonr.saferoute.screens.details.PokemonDetailsScreen
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
                            startRouteSpec = Routes.Home
                        ) {

                            composable(Routes.Home) {
                                HomeScreen(
                                    onShowPokedex = { navController.navigate("pokedex") },
                                    onDeeplink = { navController.navigateToPokemonSelector() }
                                )
                            }

                            navigationPokedex(navController)

                            dialog(Routes.PokemonSelector) {
                                PokemonSelector(onSelected = {
                                    navController.popBackStack()
                                    triggerNotification(it)
                                })
                            }
                        }
                    }
                }
            }
        }
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialNavigationApi
    private fun NavGraphBuilder.navigationPokedex(navController: NavController) {
        navigation("pokedex", Routes.PokemonList) {
            composable(Routes.PokemonList) {
                PokemonListScreen(onPokemonClick = {
                    navController.navigateToPokemonDetails(it.id)
                })
            }

            composable(Routes.PokemonDetails) {
                PokemonDetailsScreen(onShowStatsClick = {
                    navController.navigateToPokemonStats(
                        name = it.name ?: "",
                        category = it.category,
                        hp = it.hp ?: 0,
                        imageRes = it.image
                    )
                })
            }

            bottomSheet(Routes.PokemonStats) {
                PokemonStatsSheet()
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