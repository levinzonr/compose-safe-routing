package cz.levinzonr.router.processor.codegen

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import cz.levinzonr.router.processor.Constants
import cz.levinzonr.router.processor.models.ModelData
import cz.levinzonr.router.processor.models.RouteData

class RoutesBuilder(val data: ModelData) {

    fun build(): TypeSpec {
        return TypeSpec.objectBuilder(Constants.FILE_ROUTES)
            .addPaths(data.routes)
            .build()
    }

    private fun TypeSpec.Builder.addPaths(routes: List<RouteData>) : TypeSpec.Builder {
        addProperties(routes.map { it.toPropertySpec() })
        return this
    }

    private fun RouteData.toPropertySpec() : PropertySpec {
        val path = buildPathWithArguments { "{$it}" }
        return PropertySpec.builder(this.path, type = String::class)
            .addModifiers(KModifier.CONST)
            .initializer("%S", path)
            .build()
    }
}