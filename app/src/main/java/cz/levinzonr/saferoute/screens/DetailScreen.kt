package cz.levinzonr.saferoute.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cz.levinzonr.saferoute.annotations.Route
import cz.levinzonr.saferoute.components.Placeholder
import cz.levinzonr.saferoute.annotations.RouteArg
import cz.levinzonr.saferoute.annotations.RouteArgType
import cz.levinzonr.saferoute.screens.args.DetailsRouteArgs


@Composable
@Route("details", args = [
    RouteArg("id", RouteArgType.StringType, false),
    RouteArg("number", RouteArgType.IntType, true, defaultValue = "1"),
])
fun DetailsScreen(args: DetailsRouteArgs, viewModel: DetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Placeholder(color = Color.Green, title = "Details $args")
}