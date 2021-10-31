package cz.levinzonr.saferoute.processor.models

internal data class DeeplinkData(
    val uriPattern: String?,
    val action: String?,
    val mimeType: String?
)