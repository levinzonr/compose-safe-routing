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
import cz.levinzonr.saferoute.core.annotations.NavGraph
import cz.levinzonr.saferoute.core.navigation
import cz.levinzonr.saferoute.core.router.LocalRouter
import cz.levinzonr.saferoute.navigation.Main
import cz.levinzonr.saferoute.navigation.MainRoutes
import cz.levinzonr.saferoute.navigation.homeScreen
import cz.levinzonr.saferoute.navigation.pokemonDetails
import cz.levinzonr.saferoute.navigation.pokemonList
import cz.levinzonr.saferoute.navigation.pokemonStats
import cz.levinzonr.saferoute.screens.PokemonSelector
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
                            navController = rememberAnimatedNavController(bottomSheetNavigator),
                            graph = Main
                        ) {
                            homeScreen {
                                val router = LocalRouter.current
                                HomeScreen(
                                    onShowPokedex = {},
                                    onDeeplink = { router.navigate(MainRoutes.HomeScreen()) }
                                )
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
