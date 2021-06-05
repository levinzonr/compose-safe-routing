package cz.levinzonr.router.processor.constants

object KDoc {
    const val ROUTES_ACTIONS = "This object contains helpers to build actions used to navigate to different routes based on @Route annotations"
    const val ROUTES_ACTIONS_FUN = "Builds an action to %S route"

    const val ROUTE_ARG = "An Arguments holder for the %S route"

    const val ROUTES_SPEC = "This object contains the description of all routes described in the app"
    const val ROUTE_SPEC_OBJ = "A description of the %S route, including its full path and an navArgs"

    const val ROUTE_SPEC_INTERFACE = "An interface to fully describe the app route for the Compose Nav Component" +
            "\n@path - full route path with arguments" +
            "\n@navArgs - list of the NamedNavArguments for this route, corresponds with @path"
}