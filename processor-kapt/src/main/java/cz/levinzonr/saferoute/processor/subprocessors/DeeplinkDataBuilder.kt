package cz.levinzonr.saferoute.processor.subprocessors

import com.levinzonr.saferoute.codegen.codegen.extensions.checkNullable
import com.levinzonr.saferoute.codegen.models.DeeplinkData
import cz.levinzonr.saferoute.processor.extensions.fieldByName

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


}