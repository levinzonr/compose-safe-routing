package cz.levinzonr.router.annotations


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Route(val path: String)