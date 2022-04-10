package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import cz.levinzonr.saferoute.processor.constants.ClassNames
import cz.levinzonr.saferoute.processor.constants.Constants
import cz.levinzonr.saferoute.processor.logger.Logger
import cz.levinzonr.saferoute.processor.models.ModelData
import cz.levinzonr.saferoute.processor.models.NavGraphData
import java.io.File

internal class NavGraphsCodegen(
    private val data: ModelData,
    private val logger: Logger
) {

    fun generate(dir: File) {
        data.navGraphs.forEach {
            FileSpec.get(data.packageName, it.createSpecProperty()).writeTo(dir)
        }
    }


    private fun NavGraphData.createSpecProperty() : TypeSpec{
        return TypeSpec.objectBuilder(graphName)
            .addSuperinterface(ClassNames.RouteGraphSpec)
            .addProperty(
                PropertySpec.builder("name", String::class, KModifier.OVERRIDE)
                    .initializer("%S", graphName)
                    .build()
            )
            .addProperty(
                PropertySpec.builder("start", ClassNames.RouteSpec.parameterizedBy(STAR), KModifier.OVERRIDE)
                    .initializer("%T", start.specClassName)
                .build()
            )
            .build()
    }
}