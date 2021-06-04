package cz.levinzonr.router.processor.extensions

import com.squareup.kotlinpoet.FileSpec
import cz.levinzonr.router.processor.Constants

fun FileSpec.Builder.importNavType() : FileSpec.Builder {
    return addImport(Constants.PACKAGE_NAVIGATION, Constants.CLASS_NAV_TYPE)
}

fun FileSpec.Builder.importNavArgument() : FileSpec.Builder {
    return addImport(Constants.PACKAGE_NAV_COMPOSE, Constants.CLASS_NAV_ARG)
}