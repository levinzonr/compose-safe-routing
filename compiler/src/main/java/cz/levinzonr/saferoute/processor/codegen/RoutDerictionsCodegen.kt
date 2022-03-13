package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import cz.levinzonr.saferoute.processor.codegen.extensions.addArguments
import cz.levinzonr.saferoute.processor.codegen.extensions.createActionFun
import cz.levinzonr.saferoute.processor.codegen.extensions.initConstructor
import cz.levinzonr.saferoute.processor.constants.ClassNames
import cz.levinzonr.saferoute.processor.models.ModelData
import cz.levinzonr.saferoute.processor.models.RouteData
import java.io.File

internal class RouteDirectionsCodegen(
    private val data: ModelData,
) {
    fun generate(file: File) {
        data.routes.forEach {
            val typeSpec = createDirectionTypeSpec(it)
            FileSpec.get(it.packageName, typeSpec)
                .writeTo(file)
        }
    }

    private fun createDirectionTypeSpec(routeData: RouteData): TypeSpec {
        return TypeSpec.classBuilder(routeData.directionName)
            .initConstructor(routeData.arguments)
            .addArguments(routeData.arguments)
            .addFunction(routeData.createRouteAction())
            .addSuperinterface(ClassNames.Direction)
            .build()
    }

    private fun RouteData.createRouteAction() : FunSpec {
        return createActionFun("toRoute")
            .addModifiers(KModifier.OVERRIDE)
            .build()
    }
}