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

    @RouteArg("nextd")
    val nextId: Int = 0
}

@Route("settings")
class Make {

    @RouteArg("userId")
    val id: String? = null
}
