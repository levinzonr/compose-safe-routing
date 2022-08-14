package com.levinzonr.saferoute.codegen.core

import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.models.RouteData
import com.squareup.kotlinpoet.ClassName

class AnnotationsResolver(private val typeHelper: TypeHelper) {

    fun resolve(from: RouteData): List<ClassName> {
        val superTypes = typeHelper.superTypes(from.routeTransitionType)
        val hasAnimation =
            superTypes.find { it == ClassNames.AnimatedRouteTransition.canonicalName } != null
        val hasBottomSheet =
            superTypes.find { it == ClassNames.BottomSheetRouteTransition.canonicalName } != null
        return listOfNotNull(
            ClassNames.ExperimentalAnimationApi.takeIf { hasAnimation },
            ClassNames.ExperimentalNavigationApi.takeIf { hasBottomSheet || from.routeTransitionClass == ClassNames.BottomSheetRouteTransition }
        )
    }
}