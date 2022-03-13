package cz.levinzonr.saferoute.core.router

import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import cz.levinzonr.saferoute.core.RouteSpec

class EmptyRouter : Router {
    override fun navigate(direction: Direction, builder: NavOptionsBuilder.() -> Unit) {
        // no-op
    }

    override fun navigate(
        direction: Direction,
        navOptions: NavOptions?,
        navigationExtras: Navigator.Extras?
    ) {
        // no-op
    }

    override fun navigateBack(): Boolean = false

    override fun popBackStack(): Boolean = true

    override fun popBackStack(
        route: RouteSpec<*>,
        inclusive: Boolean,
        saveState: Boolean
    ): Boolean = false

    override fun clearBackStack(route: RouteSpec<*>): Boolean = false
}