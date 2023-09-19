package com.levinzonr.saferoute.codegen.models

import com.levinzonr.saferoute.codegen.core.Source

data class NavGraphInfo(
    val name: String,
    val qualifiedName: String,
    val simpleName: String,
    val source: Source
) {

    constructor(qualifiedName: String, simpleName: String, source: Source) : this(
        name = simpleName,
        simpleName = simpleName,
        qualifiedName = qualifiedName,
        source = source
    )
}
