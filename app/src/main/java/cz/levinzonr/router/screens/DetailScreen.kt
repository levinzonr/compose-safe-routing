package cz.levinzonr.router.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cz.levinzonr.router.annotations.Route
import cz.levinzonr.router.annotations.RouteArg
import cz.levinzonr.router.components.Placeholder
import cz.levinzonr.router.screens.args.DetailsRouteArgs


@Composable
@Route("details", arguments = [
    RouteArg("id", String::class),
    RouteArg("a", Float::class),
    RouteArg("bboold", Boolean::class),
    RouteArg("lonaa", Long::class)
])
fun DetailsScreen(args: DetailsRouteArgs, viewModel: DetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Placeholder(color = Color.Green, title = "Details $args")
}