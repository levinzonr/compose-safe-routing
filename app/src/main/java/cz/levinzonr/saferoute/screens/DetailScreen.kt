package cz.levinzonr.saferoute.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cz.levinzonr.saferoute.components.Placeholder
import cz.levinzonr.saferoute.core.annotations.Route
import cz.levinzonr.saferoute.core.annotations.RouteArg
import cz.levinzonr.saferoute.screens.args.DetailsRouteArgs

@Composable
@Route("details", args = [
    RouteArg("id", String::class, false, isNullable = true),
    RouteArg("number", Int::class, true, defaultValue = "1"),
])
fun DetailsScreen(args: DetailsRouteArgs, viewModel: DetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Placeholder(color = Color.Green, title = "Details $args")
}