package cz.levinzonr.saferoute.processor.typehelper

import com.levinzonr.saferoute.codegen.core.TypeHelper
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Types

class TypeHelperImpl(
    private val typeUtils: Types
) : TypeHelper {

    override fun superTypes(value: Any?): List<String> {
        if (value == null) return emptyList()
        return if (value is TypeMirror) {
            typeUtils.directSupertypes(value).map { it.toString() }
        } else {
            emptyList()
        }
    }
}
