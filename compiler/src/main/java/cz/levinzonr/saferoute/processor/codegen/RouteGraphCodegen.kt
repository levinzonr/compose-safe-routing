package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import cz.levinzonr.saferoute.processor.constants.ClassNames
import cz.levinzonr.saferoute.processor.logger.Logger
import cz.levinzonr.saferoute.processor.models.ModelData
import cz.levinzonr.saferoute.processor.models.NavGraphData
import java.io.File

internal class RouteGraphCodegen(
    private val data: ModelData,
    private val logger: Logger
) {

    fun generate(dir: File) {
        val typeSpec = TypeSpec.objectBuilder("RouteNavGraphs")
        data.navGraphs.forEach {
            typeSpec.addProperty(it.createSpecProperty())
        }
        FileSpec.get(data.packageName, typeSpec.build()).writeTo(dir)
    }


    private fun NavGraphData.createSpecProperty() : PropertySpec{
        val type = TypeSpec.anonymousClassBuilder()
            .addSuperinterface(ClassNames.RouteGraphSpec)
            .addProperty(
                PropertySpec.builder("name", String::class, KModifier.OVERRIDE)
                    .initializer("%S", name)
                    .build()
            )
            .addProperty(
                PropertySpec.builder("start", ClassNames.RouteSpec.parameterizedBy(STAR), KModifier.OVERRIDE)
                    .initializer(start.specName)
                .build()
            )
            .build()

        return PropertySpec.builder(graphSpecName, ClassNames.RouteGraphSpec)
            .initializer("%L", type)
            .build()
    }
}