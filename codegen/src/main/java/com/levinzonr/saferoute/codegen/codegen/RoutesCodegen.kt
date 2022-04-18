package com.levinzonr.saferoute.codegen.codegen

import com.squareup.kotlinpoet.*
import com.levinzonr.saferoute.codegen.codegen.extensions.*
import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.constants.KDoc
import com.levinzonr.saferoute.codegen.core.FilesGen
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.NavGraphData


 object RoutesCodegen: FilesGen {

     override fun generate(data: ModelData): List<FileSpec> {
         return listOf(FileSpec.get(data.packageName, data.build()))
     }

     fun ModelData.build(): TypeSpec {
        val mainGraph = navGraphs.firstOrNull() { it.name == "main" }
        val graphs = navGraphs.filterNot { it.name == "main" }
        return TypeSpec.objectBuilder(Constants.FILE_ROUTES)
            .addProperties(mainGraph?.routes.orEmpty().map { it.toRouteProperty() })
            .addProperties(graphs.map { it.toRoutesProperty(packageName) })
            .deprecate(
                message ="Routes.kt is deprecated, please use GraphRoutes object or Routes directly",
                replaceWithExpression = "${mainGraph?.graphName}Routes",
                ClassName(packageName, "${mainGraph?.graphName}Routes")
            )
            .addKdoc(KDoc.ROUTES_SPEC)
            .build()
    }

    private fun NavGraphData.toRoutesProperty(packageName: String): PropertySpec {
        val className = ClassName(packageName, "${graphName}Routes")
        return PropertySpec.builder(
            "${name.capitalize()}Routes",
            ClassName(packageName, "${graphName}Routes")
        )
            .initializer("%T", className)
            .build()
    }

}