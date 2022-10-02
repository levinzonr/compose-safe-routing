package com.levinzonr.saferoute.codegen.constants

import com.squareup.kotlinpoet.ClassName

internal object ClassNames {
    val Direction = ClassName("${Constants.PACKAGE_CORE}.router", "Direction")
    val RouteArgsFactory = ClassName(Constants.PACKAGE_CORE, "RouteArgsFactory")
    val RouteSpec = ClassName(Constants.PACKAGE_CORE, Constants.FILE_ROUTE_SPEC)
    val RouteGraphSpec = ClassName(Constants.PACKAGE_CORE, "NavGraphSpec")
    val EmptyArgsFactory = ClassName("${Constants.PACKAGE_CORE}.util", "EmptyArgsFactory")
    val Bundle = ClassName("android.os", "Bundle")
    val SavedStateHandle = ClassName("androidx.lifecycle", "SavedStateHandle")
    val NamedNavArgument = ClassName(Constants.PACKAGE_NAVIGATION, "NamedNavArgument")
    val navArgument = ClassName(Constants.PACKAGE_NAVIGATION, "navArgument")
    val NavType = ClassName(Constants.PACKAGE_NAVIGATION, "NavType")
    val NavController = ClassName(Constants.PACKAGE_NAVIGATION, "NavController")
    val ProvidableCompositionLocal = ClassName(Constants.PACKAGE_COMPOSE_RUNTIME, "ProvidableCompositionLocal")
    val compositionLocalOf = ClassName(Constants.PACKAGE_COMPOSE_RUNTIME, "compositionLocalOf")
    val Composable = ClassName(Constants.PACKAGE_COMPOSE_RUNTIME, "Composable")
    val NavDeepLink = ClassName(Constants.PACKAGE_NAVIGATION, "NavDeepLink")
    val NavDeepLinkDSL = ClassName(Constants.PACKAGE_NAVIGATION, "navDeepLink")
    val NavGraphBuilder = ClassName(Constants.PACKAGE_NAVIGATION, "NavGraphBuilder")
    val NavBackStackEntry = ClassName(Constants.PACKAGE_NAVIGATION, "NavBackStackEntry")
    val route = ClassName(Constants.PACKAGE_CORE, "route")
    val navigateTo = ClassName(Constants.PACKAGE_CORE, "navigateTo")
    val navigation = ClassName(Constants.PACKAGE_CORE, "navigation")
    val BottomSheetRouteTransition = ClassName(Constants.PACKAGE_ACCOMPANIST + ".transitions", "BottomSheetRouteTransition")
    val AnimatedRouteTransition = ClassName(Constants.PACKAGE_ACCOMPANIST + ".transitions", "AnimatedRouteTransition")
    val ExperimentalAnimationApi = ClassName("androidx.compose.animation", "ExperimentalAnimationApi")
    val ExperimentalNavigationApi = ClassName("com.google.accompanist.navigation.material", "ExperimentalMaterialNavigationApi")
}
