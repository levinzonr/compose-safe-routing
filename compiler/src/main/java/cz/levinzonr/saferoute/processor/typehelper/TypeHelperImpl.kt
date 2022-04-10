package cz.levinzonr.saferoute.processor.typehelper

import com.squareup.kotlinpoet.ClassName
import com.levinzonr.saferoute.codegen.constants.ClassNames
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Types

class TypeHelperImpl(
    private val typeUtils: Types
) : TypeHelper<TypeMirror> {


    override fun resolveNeededAnnotations(value: TypeMirror): List<ClassName> {
        val superTypes = typeUtils.directSupertypes(value)
        val hasAnimation =
            superTypes.find { it.toString() == ClassNames.AnimatedRouteTransition.canonicalName } != null
        val hasBottomSheet =
            superTypes.find { it.toString() == ClassNames.BottomSheetRouteTransition.canonicalName } != null
        return listOfNotNull(
            ClassNames.ExperimentalAnimationApi.takeIf { hasAnimation },
            ClassNames.ExperimentalNavigationApi.takeIf { hasBottomSheet || value.toString() == ClassNames.BottomSheetRouteTransition.canonicalName }
        )
    }
}