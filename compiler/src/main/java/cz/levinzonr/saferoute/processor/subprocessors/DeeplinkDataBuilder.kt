package cz.levinzonr.saferoute.processor.subprocessors

import cz.levinzonr.saferoute.processor.extensions.fieldByName
import com.levinzonr.saferoute.codegen.models.DeeplinkData

internal object DeeplinkDataBuilder {
    fun build(annotation: Annotation) : DeeplinkData {
        return with(annotation) {
            DeeplinkData(
                uriPattern = annotation.fieldByName<String>("pattern").checkNullable(),
                action = fieldByName<String>("action").checkNullable(),
                mimeType = fieldByName<String>("mimeType").checkNullable()
            )
        }
    }

    private fun String.checkNullable(): String? {
        return takeUnless { it == "@null" }
    }
}