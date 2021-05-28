package cz.levinzonr.router.processor.codegen

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import cz.levinzonr.router.processor.models.RouteData

class RoutesBuilder(
    val data: List<RouteData>
) {
    fun build() : TypeSpec {
        return TypeSpec.objectBuilder("Routes")
            .addFunctions(data.map { it.funSpec() }.toMutableList())
            .build()
    }

    private fun RouteData.funSpec() : FunSpec {
        val builder = FunSpec.builder("navigateTo${name.capitalize()}")

        arguments.forEach {
            builder.addParameter(it.name, Class.forName(it.type).kotlin)
        }



        builder.addStatement("return \"$path\"")
/*
        if (arguments.isEmpty()) {
        } else {
            val path =  "${path}${arguments.joinToString(prefix = "/", separator = "/",) {"\${${it.name}}"} }"
            builder.addStatement("return $path")
        }*/

        return builder.returns(returnType = String::class).build()
    }
}