package com.levinzonr.saferoute.codegen.codegen

import com.levinzonr.saferoute.codegen.codegen.extensions.toRouteProperty
import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.core.GeneratorUnit
import com.levinzonr.saferoute.codegen.core.Logger
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.NavGraphData
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.TypeSpec

class NavGraphsCodegen(
    private val logger: Logger
) : FilesGen {

    override fun generate(data: ModelData): List<GeneratorUnit> {
        return data.navGraphs.map {
            GeneratorUnit(
                FileSpec.get(it.packageName, it.createSpecProperty()),
                it.sources
            )
        }
    }

    private fun NavGraphData.createSpecProperty(): TypeSpec {
        return TypeSpec.objectBuilder(graphName)
            .addSuperinterface(ClassNames.RouteGraphSpec.parameterizedBy(scopeClassName))
            .addProperty(
                PropertySpec.builder("name", String::class, KModifier.OVERRIDE)
                    .initializer("%S", graphName)
                    .build()
            )
            .addProperty(
                PropertySpec.builder(
                    "start",
                    ClassNames.RouteSpec.parameterizedBy(STAR),
                    KModifier.OVERRIDE
                )
                    .initializer("%T", start.specClassName)
                    .build()
            )
            .addFunction(
                FunSpec.builder("provideGraphScope")
                    .addModifiers(KModifier.OVERRIDE)
                    .returns(scopeClassName)
                    .addParameter("graphBuilder", ClassNames.NavGraphBuilder)
                    .addCode("return %T(graphBuilder)", scopeBuilderClassName)
                    .build()
            )
            .apply {
                routes.forEach {
                    addProperty(it.toRouteProperty())
                }
            }
            .build()
    }

    private fun createGraph(graphData: NavGraphData): TypeSpec {
        val builder = TypeSpec.objectBuilder("${graphData.graphName}Routes")
        val routeProperties = graphData.routes.map { it.toRouteProperty() }
        builder.addProperties(routeProperties)
        return builder.build()
    }
}
