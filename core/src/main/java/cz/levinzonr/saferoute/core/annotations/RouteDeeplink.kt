package cz.levinzonr.saferoute.core.annotations

import androidx.navigation.NavDeepLinkRequest

/**
 * Describes the deeplink that is supported by this route
 * @param pattern  URI pattern of the deeplink i.e app://deeplink/path or https://example.org
 * @param mimeType  The Deeplinks  mimeType
 * @param action  deeplink action
 * @see NavDeepLinkRequest for more info
*/
@Retention(AnnotationRetention.SOURCE)
annotation class RouteDeeplink(
    val pattern: String = AnnotationsDefaults.NULL,
    val mimeType: String = AnnotationsDefaults.NULL,
    val action: String = AnnotationsDefaults.NULL
)
