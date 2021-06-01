package cz.levinzonr.router.processor.extensions

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec


fun FunSpec.Builder.addCodeBlocks(list: List<CodeBlock>) : FunSpec.Builder {
    list.forEach { addCode(it); addCode("\n") }
    return this
}