package cz.levinzonr.saferoute.core

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import cz.levinzonr.saferoute.core.router.Direction

fun NavController.navigateTo(
    direction: Direction,
    navOptions: NavOptions? = null,
    navigationExtras: Navigator.Extras? = null
) = navigate(
    route = direction.route,
    navOptions = navOptions,
    navigatorExtras = navigationExtras
)

fun NavController.navigateTo(
    direction: Direction,
    builder: NavOptionsBuilder.() -> Unit = {}
) = navigate(
    route = direction.route,
    builder = builder
)
