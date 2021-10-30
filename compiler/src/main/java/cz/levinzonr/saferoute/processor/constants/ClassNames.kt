package cz.levinzonr.saferoute.processor.constants

import com.squareup.kotlinpoet.ClassName

internal object ClassNames {
    val RouteArgsFactory = ClassName(Constants.PACKAGE_CORE, "RouteArgsFactory")
    val RouteSpec = ClassName(Constants.PACKAGE_CORE, Constants.FILE_ROUTE_SPEC)
    val EmptyArgsFactory = ClassName("${Constants.PACKAGE_CORE}.util", "EmptyArgsFactory")
    val Bundle = ClassName("android.os", "Bundle")
    val SavedStateHandle = ClassName("androidx.lifecycle", "SavedStateHandle")
    val NamedNavArgument =  ClassName(Constants.PACKAGE_NAVIGATION, "NamedNavArgument")
    val navArgument = ClassName(Constants.PACKAGE_NAVIGATION, "navArgument")
    val NavType = ClassName(Constants.PACKAGE_NAVIGATION, "NavType")
    val NavController = ClassName(Constants.PACKAGE_NAVIGATION, "NavController")
    val ProvidableCompositionLocal = ClassName(Constants.PACKAGE_COMPOSE_RUNTIME, "ProvidableCompositionLocal")
    val compositionLocalOf = ClassName(Constants.PACKAGE_COMPOSE_RUNTIME, "compositionLocalOf")
    val Composable = ClassName(Constants.PACKAGE_COMPOSE_RUNTIME, "Composable")
    val NavDeepLink = ClassName(Constants.PACKAGE_NAVIGATION, "NavDeepLink")
    val NavDeepLinkDSL = ClassName(Constants.PACKAGE_NAVIGATION, "navDeepLink")
    val NavGraphBuilder = ClassName(Constants.PACKAGE_NAVIGATION, "NavGraphBuilder")

    val DefaultRouteTransition = ClassName(Constants.PACKAGE_CORE, "DefaultRouteTransition")
    val BottomSheetRouteTransition = ClassName(Constants.PACKAGE_ACCOMPANIST + ".transitions", "BottomSheetRouteTransition")
    val AnimatedRouteTransition = ClassName(Constants.PACKAGE_ACCOMPANIST + ".transitions", "AnimatedRouteTransition")

    val bottomSheetTransition = ClassName(Constants.PACKAGE_ACCOMPANIST, "bottomSheet")
    val animatedComposableTransition = ClassName(Constants.PACKAGE_ACCOMPANIST, "composable")
    val composableTransition = ClassName(Constants.PACKAGE_CORE, "composable")
    val ExperimentalAnimationApi = ClassName("androidx.compose.animation", "ExperimentalAnimationApi")
    val ExperimentalNavigationApi = ClassName("com.google.accompanist.navigation.material", "ExperimentalMaterialNavigationApi")
}

