package cz.levinzonr.saferoute.core.router

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import cz.levinzonr.saferoute.core.RouteSpec
import cz.levinzonr.saferoute.core.navigateTo

class RouterImpl(private val navController: NavController) : Router {
    override fun navigate(direction: Direction, builder: NavOptionsBuilder.() -> Unit) {
        navController.navigateTo(direction, builder)
    }

    override fun navigate(
        direction: Direction,
        navOptions: NavOptions?,
        navigationExtras: Navigator.Extras?
    ) {
        navController.navigateTo(direction, navOptions, navigationExtras)
    }

    override fun navigateUp() : Boolean {
        return navController.navigateUp()
    }

    override fun popBackStack() : Boolean {
        return navController.popBackStack()
    }


    override fun popBackStack(
        route: RouteSpec<*>,
        inclusive: Boolean,
        saveState: Boolean,
    ): Boolean {
        return navController.popBackStack(route.route, inclusive, saveState)
    }

    override fun clearBackStack(route: RouteSpec<*>): Boolean {
        return navController.clearBackStack(route.route)
    }

}


