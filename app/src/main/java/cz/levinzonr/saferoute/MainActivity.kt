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
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import cz.levinzonr.saferoute.accompanist.navigation.AnimatedNavHost
import cz.levinzonr.saferoute.accompanist.navigation.bottomSheetWithArgs
import cz.levinzonr.saferoute.accompanist.navigation.composableWithArgs
import cz.levinzonr.saferoute.screens.DetailsScreen
import cz.levinzonr.saferoute.screens.ProfileScreen
import cz.levinzonr.saferoute.screens.Routes
import cz.levinzonr.saferoute.screens.RoutesActions
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
                    val controller = rememberAnimatedNavController()
                    val bottomSheetNavigator = rememberBottomSheetNavigator()
                    controller.navigatorProvider += bottomSheetNavigator

                    ModalBottomSheetLayout(bottomSheetNavigator) {
                        AnimatedNavHost(
                            navController = controller,
                            startRouteSpec = Routes.Profile
                        ) {
                            composableWithArgs(Routes.Profile) { _, _ ->
                                ProfileScreen {
                                    controller.navigate(RoutesActions.toDetails("ID", 0))
                                }
                            }
                            bottomSheetWithArgs(Routes.Details) { _, args ->
                                DetailsScreen(args = args, hiltViewModel())
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