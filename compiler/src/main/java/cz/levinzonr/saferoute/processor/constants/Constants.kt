package cz.levinzonr.saferoute.processor.constants

import com.squareup.kotlinpoet.ClassName

internal object Constants {
    const val FILE_ARGS_EXTENSIONS = "args-extensions"
    const val FILE_ACTIONS = "RoutesActions"
    const val FILE_ARGS_POSTFIX = "RouteArgs"
    const val FILE_ROUTES = "Routes"
    const val FILE_ROUTE_SPEC = "RouteSpec"

    const val ROUTE_SPEC_NAME = "route"
    const val ROUTE_SPEC_ARGS = "navArgs"

    const val FILE_ARG_EXTENSIONS_PREFIX = "NavBackStackEntry+"
    const val FILE_ARGS_DIR = "args"
    const val ACTIONS_PREFIX = "to"

    const val PACKAGE_NAVIGATION = "androidx.navigation"
    const val CLASSNAME_NAV_BACK_STACK_ENTRY = "NavBackStackEntry"
    val CLASS_BACK_STACK_ENTRY = ClassName(PACKAGE_NAVIGATION, CLASSNAME_NAV_BACK_STACK_ENTRY)


    const val PACKAGE_NAV_COMPOSE = "$PACKAGE_NAVIGATION.compose"
    const val CLASS_NAV_TYPE = "NavType"
    const val CLASS_NAV_ARG = "navArgument"
    const val CLASSNAME_NAMED_ARG = "NamedNavArgument"



    const val PACKAGE_LIFECYCLE = "androidx.lifecycle"
    const val CLASSNAME_SAVED_STATE_HANDLE = "SavedStateHandle"
    val CLASS_SAVED_STATE_HANDLE = ClassName(PACKAGE_LIFECYCLE, CLASSNAME_SAVED_STATE_HANDLE)
    val CLASS_NAMED_ARG = ClassName(PACKAGE_NAV_COMPOSE, CLASSNAME_NAMED_ARG)

}

object ClassNames {
    val Bundle = ClassName("android.os", "Bundle")
    val SavedStateHandle = ClassName(Constants.PACKAGE_LIFECYCLE, Constants.CLASSNAME_SAVED_STATE_HANDLE)
}



