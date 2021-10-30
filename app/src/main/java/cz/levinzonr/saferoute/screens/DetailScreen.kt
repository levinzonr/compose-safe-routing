package cz.levinzonr.saferoute.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import cz.levinzonr.saferoute.accompanist.navigation.transitions.BottomSheetRouteTransition
import cz.levinzonr.saferoute.components.Placeholder
import cz.levinzonr.saferoute.core.annotations.Route
import cz.levinzonr.saferoute.core.annotations.RouteArg
import cz.levinzonr.saferoute.core.annotations.RouteDeeplink
import cz.levinzonr.saferoute.screens.args.DetailsRouteArgs
import cz.levinzonr.saferoute.transitions.FadeInFadeOutTransition


@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
@Composable
@Route(
    name = "details",
    args = [
        RouteArg("id", String::class, false),
        RouteArg("number", Int::class, true, defaultValue = "1"),
    ],
    deepLinks = [
        RouteDeeplink("app://deeplink/{id}"),
        RouteDeeplink("app://deeplink/{id}?number={number}")
    ],
    transition = BottomSheetRouteTransition::class
)
fun DetailsScreen(
    args: DetailsRouteArgs,
    viewModel: DetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Placeholder(color = Color.Green, title = "Details $args")
}