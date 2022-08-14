package cz.levinzonr.saferoute.playground

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import cz.levinzonr.saferoute.core.route
import cz.levinzonr.saferoute.core.transitions.DefaultRouteTransition
import cz.levinzonr.saferoute.screens.home.HomeScreenRoute


interface Spec<T> {
    val route: String
    val name: String

    fun buildScope(builder: NavGraphBuilder) : T
}

interface LoginScope {
    fun login(content: @Composable () -> Unit = {A()})
    fun signup(content: @Composable () -> Unit)
}

interface MainScope {
    fun home(content: @Composable () -> Unit)
    fun loginGraph(scope: LoginScope.() -> Unit)
}
object LoginGraph : Spec<LoginScope> {
    override val route: String
        get() = TODO("Not yet implemented")
    override val name: String
        get() = TODO("Not yet implemented")

    override fun buildScope(builder: NavGraphBuilder): LoginScope {
        return object : LoginScope {
            override fun login(content: @Composable () -> Unit) {

            }

            override fun signup(content: @Composable () -> Unit) {
            }
        }
    }
}

class MainScopeImpl(val navGraphBuilder: NavGraphBuilder) : MainScope {



    override fun home(content: @Composable () -> Unit) {
        navGraphBuilder.route(HomeScreenRoute, DefaultRouteTransition, content)
    }

    override fun loginGraph(scope: LoginScope.() -> Unit) {
        navGraphBuilder.navigation(LoginGraph, scope)
    }


}

object MainNavGraph: Spec<MainScope> {
    override val route: String
        get() = "name"
    override val name: String
        get() = "name"

    override fun buildScope(builder: NavGraphBuilder): MainScope {
        return MainScopeImpl(builder)
    }
}

fun<T> NavGraphBuilder.navigation(
    spec: Spec<T>,
    content: T.() -> Unit
) = navigation(startDestination = spec.route, route = spec.name) {
    spec.buildScope(this).apply(content)
}

fun NavGraphBuilder.loginGraph(scope: LoginScope.() -> Unit) {
    navigation(startDestination = LoginGraph.name, LoginGraph.route) {
        LoginGraph.buildScope(this).apply(scope)
    }
}

@Composable
fun A() {
   ScopedNavHost(graph = MainNavGraph) {
       loginGraph {
           login {  }
           signup {  }
       }
       home {

       }
   }
}

@Composable
fun<T> ScopedNavHost(
    navController: NavHostController = rememberNavController(),
    graph: Spec<T>,
    scope: T.() -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = graph.name,
    ) {
        graph.buildScope(this).apply(scope)
    }
}
