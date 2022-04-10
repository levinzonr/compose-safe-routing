package cz.levinzonr.saferoute.processor.codegen.extensions

import com.squareup.kotlinpoet.*


fun FunSpec.Builder.deprecate(
    message: String,
    replaceWithExpression: String? = null,
    vararg imports: ClassName = arrayOf()
) : FunSpec.Builder {
    addAnnotation(createDeprecateAnnotation(message, replaceWithExpression, *imports))
    return this
}

fun TypeSpec.Builder.deprecate(
    message: String,
    replaceWithExpression: String? = null,
    vararg imports: ClassName = arrayOf()
): TypeSpec.Builder {
    addAnnotation(createDeprecateAnnotation(message, replaceWithExpression, *imports))
    return this
}

private fun createDeprecateAnnotation(
    message: String,
    replaceWithExpression: String?,
    vararg imports: ClassName
): AnnotationSpec {
    val annotation = AnnotationSpec.builder(Deprecated::class)
        .addMember("message = %S", message)

    replaceWithExpression?.let {
        val importsStrings = imports.map { "\"${it.canonicalName}\"" }.joinToString(",")
        val replaceWithAnnotation = AnnotationSpec.builder(ReplaceWith::class)
            .addMember("expression = %S", it)
            .addMember("imports = arrayOf($importsStrings)")
            .build()

        annotation.addMember("replaceWith = %L", replaceWithAnnotation)

    }
    return annotation.build()
}