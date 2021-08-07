package cz.levinzonr.saferoute.processor.pathbuilder

import cz.levinzonr.saferoute.processor.models.ArgumentData

internal typealias ArgumentPathBuilder = (ArgumentData) -> String

internal class PathBuilder(val args: List<ArgumentData>) {
    var separator: String = "/"
    var prefix: String = ""
    var argBuilder: ArgumentPathBuilder = { it.name }

    fun build(): String {
        return args.joinToString(separator = separator, prefix = prefix) {
            argBuilder.invoke(it)
        }
    }
}

internal fun pathBuilder(args: List<ArgumentData>, builder: PathBuilder.() -> Unit) : String {
    return PathBuilder(args).apply(builder).build()
}

internal fun optionalPathBuilder(args: List<ArgumentData>, builder: ArgumentPathBuilder) : String {
    return pathBuilder(args) {
        separator = "&"
        prefix = if (args.isEmpty()) "" else "?"
        argBuilder = builder
    }
}

internal fun fullPathBuilder(args: List<ArgumentData>, navBuilder: ArgumentPathBuilder, optionalBuilder: ArgumentPathBuilder) : String {
    val navPath = navPathBuilder(args.filter { it.optionalData == null }, navBuilder)
    val optionalPath = optionalPathBuilder(args.filter { it.optionalData != null }, optionalBuilder)
    return "$navPath$optionalPath"
}

internal fun navPathBuilder(args: List<ArgumentData>, builder: ArgumentPathBuilder) : String {
    return pathBuilder(args) {
        separator = "/"
        prefix = if (args.isEmpty()) "" else "/"
        argBuilder = builder
    }
}