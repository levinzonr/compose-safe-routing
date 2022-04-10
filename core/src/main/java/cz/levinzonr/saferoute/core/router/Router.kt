package cz.levinzonr.saferoute.core.router

import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import cz.levinzonr.saferoute.core.RouteSpec

interface Router {

    fun navigate(
        direction: Direction,
        builder: NavOptionsBuilder.() -> Unit = {}
    )
    fun navigate(
        direction: Direction,
        navOptions: NavOptions? = null,
        navigationExtras: Navigator.Extras? = null
    )

    fun navigateUp() : Boolean

    fun popBackStack() : Boolean

    fun popBackStack(
        route: RouteSpec<*>,
        inclusive: Boolean,
        saveState: Boolean = false,
    ): Boolean

    fun clearBackStack(route: RouteSpec<*>): Boolean
    
}