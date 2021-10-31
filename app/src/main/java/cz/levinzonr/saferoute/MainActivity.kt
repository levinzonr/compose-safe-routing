package cz.levinzonr.saferoute

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.navDeepLink
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import cz.levinzonr.saferoute.accompanist.navigation.*
import cz.levinzonr.saferoute.core.currentArgs
import cz.levinzonr.saferoute.core.dialog
import cz.levinzonr.saferoute.screens.*
import cz.levinzonr.saferoute.screens.args.LocalDetailsRouteArgs
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
                            composable(Routes.Profile    ) {
        val args = Routes.Profile.currentArgs
        ProfileScreen {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("app://deeplink/hello?number=232")
    })
        }
                            }
                            detailsRoute {
                                DetailsScreen(args = LocalDetailsRouteArgs.current, hiltViewModel())
                            }

                            settingsRoute {
                                SettingsScreen()
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