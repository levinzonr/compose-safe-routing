package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.levinzonr.saferoute.codegen.constants.ClassNames
import com.levinzonr.saferoute.codegen.models.RouteData

internal class RouteArgsProviderBuilder(
    val data: RouteData
) {
    fun build(): FileSpec {
        return FileSpec.builder(data.argsPackageName, "Local${data.argumentsName}")
            .addProperty(createLocalArgsProperty())
            .build()
    }


    private fun createLocalArgsProperty(): PropertySpec {
        return PropertySpec.builder(
            name = "Local${data.argumentsName}",
            type = ClassNames.ProvidableCompositionLocal.parameterizedBy(data.argsTypeClassName)
        ).initializer(
            InitTemplate,
            ClassNames.compositionLocalOf,
            data.argsTypeClassName,
            data.argumentsName + " are not provided"
        ).build()
    }

    private val InitTemplate = "%T<%T> {\n" +
            "    error(%S)\n" +
            "}"
}