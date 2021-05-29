package cz.levinzonr.router

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.levinzonr.router.screens.DetailsScreen
import cz.levinzonr.router.screens.ProfileScreen
import cz.levinzonr.router.screens.Routes
import cz.levinzonr.router.screens.RoutesActions
import cz.levinzonr.router.ui.theme.RouterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RouterTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val controller = rememberNavController()
                    NavHost(navController = controller, startDestination = "profile") {
                        composable(Routes.profile) {
                            ProfileScreen { controller.navigate(RoutesActions.toDetails("newId"))}
                        }
                        composable(Routes.details) {
                            DetailsScreen()
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