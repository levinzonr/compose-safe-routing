package com.levinzonr.saferoute.codegen.codegen.extensions

fun String.checkNullable(): String? {
    return takeUnless { it == "@null" }
}