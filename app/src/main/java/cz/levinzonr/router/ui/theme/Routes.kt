package cz.levinzonr.router.ui.theme

import cz.levinzonr.router.annotations.Route
import cz.levinzonr.router.annotations.RouteArg


@Route("profile")
object SimpleRoute
@Route("settings")
object SimpleRoutes

@Route("details")
class Details {

    @RouteArg("id")
    val id: String = ""

    @RouteArg("another")
    val anotherId : Boolean = false

    @RouteArg("nextd")
    val nextId: Float = 24.24f
}

@Route("settings")
class Make {

    @RouteArg("userId")
    val id: String? = null
}
