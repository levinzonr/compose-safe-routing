package cz.levinzonr.router.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cz.levinzonr.router.core.Route
import cz.levinzonr.router.components.Placeholder
import cz.levinzonr.router.core.RouteArg
import cz.levinzonr.router.core.RouteArgType
import cz.levinzonr.router.screens.args.DetailsRouteArgs


@Composable
@Route("details", args = [
    RouteArg("id", RouteArgType.ArgStringNonNull, false),
    RouteArg("anotherId", RouteArgType.ArgStringNonNull, false),
    RouteArg("number", RouteArgType.ArgInt, true, defaultValue = "1"),
    RouteArg("floatvalue", RouteArgType.ArgStringNullable)
])
fun DetailsScreen(args: DetailsRouteArgs, viewModel: DetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Placeholder(color = Color.Green, title = "Details $args")
}