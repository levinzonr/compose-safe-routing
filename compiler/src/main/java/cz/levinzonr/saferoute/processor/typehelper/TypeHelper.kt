package cz.levinzonr.saferoute.processor.typehelper

import com.squareup.kotlinpoet.ClassName

interface TypeHelper<T> {
    fun resolveNeededAnnotations(value: T) : List<ClassName>
}