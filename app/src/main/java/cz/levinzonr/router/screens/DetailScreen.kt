package cz.levinzonr.router.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cz.levinzonr.router.core.Route
import cz.levinzonr.router.components.Placeholder
import cz.levinzonr.router.core.args.RouteBoolArg
import cz.levinzonr.router.core.args.RouteFloatArg
import cz.levinzonr.router.core.args.RouteIntArg
import cz.levinzonr.router.core.args.RouteLongArg
import cz.levinzonr.router.core.args.RouteStringArg
import cz.levinzonr.router.screens.args.DetailsRouteArgs



@Composable
@Route("details")
@RouteStringArg("id", false)
fun DetailsScreen(args: DetailsRouteArgs, viewModel: DetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Placeholder(color = Color.Green, title = "Details $args")
}