package cz.levinzonr.saferoute

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import cz.levinzonr.saferoute.accompanist.navigation.AnimatedNavHost
import cz.levinzonr.saferoute.accompanist.navigation.composableWithArgs
import cz.levinzonr.saferoute.screens.DetailsScreen
import cz.levinzonr.saferoute.screens.ProfileScreen
import cz.levinzonr.saferoute.screens.Routes
import cz.levinzonr.saferoute.screens.RoutesActions
import cz.levinzonr.saferoute.ui.theme.RouterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RouterTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val controller = rememberAnimatedNavController()
                    AnimatedNavHost(navController = controller, startRouteSpec = Routes.Profile) {
                        composableWithArgs(Routes.Profile) { _, _ ->
                            ProfileScreen {
                                controller.navigate(RoutesActions.toDetails("ID", 0))
                            }
                        }
                        composableWithArgs(Routes.Details) { _, args ->
                            DetailsScreen(args = args, hiltViewModel())
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