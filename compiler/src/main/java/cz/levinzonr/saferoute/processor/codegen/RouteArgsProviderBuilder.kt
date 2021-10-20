package cz.levinzonr.saferoute.processor.codegen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import cz.levinzonr.saferoute.processor.constants.ClassNames
import cz.levinzonr.saferoute.processor.extensions.ComposableFunction
import cz.levinzonr.saferoute.processor.models.RouteData

internal class RouteArgsProviderBuilder(
    val data: RouteData
) {
    fun build(): FileSpec {
        return FileSpec.builder(data.packageName, data.getArgsProviderName())
            .addProperty(createLocalArgsProperty())
            .addFunction(createLocalProviderFunc())
            .build()
    }


    private fun createLocalArgsProperty(): PropertySpec {
        return PropertySpec.builder(
            name = "Local${data.argumentsName}",
            type = ClassNames.CompositionLocal.parameterizedBy(data.argsTypeClassName)
        ).initializer(
            InitTemplate,
            ClassNames.compositionLocalOf,
            data.argsTypeClassName,
            data.argumentsName + " are not provided"
        ).build()
    }

    private fun createLocalProviderFunc(): FunSpec {

        return FunSpec.builder("${data.argumentsName}Provider")
            .addAnnotation(ClassNames.Composable)
            .addParameter(name = "entry", ClassNames.NavBackStackEntry)
            .addParameter(
                name = "spec",
                ClassNames.RouteSpec.parameterizedBy(data.argsTypeClassName)
            )
            .addParameter(ParameterSpec.builder(name = "content", ComposableFunction).build())
            .addCode(
                CodeBlock.builder().indent()
                    .addStatement("val args = spec.argsFactory.fromBundle(entry.arguments)")
                    .beginControlFlow(
                        "%T(Local${data.argumentsName} provides args)",
                        ClassNames.CompositionLocalProvider
                    )
                    .addStatement("content.invoke()")
                    .endControlFlow()
                    .build()
            ).build()
    }

    /* @Composable
     fun<T> ArgsProvider(backStackEntry: NavBackStackEntry, spec: RouteSpec<T>, content: @Composable () -> Unit) {
         val args = spec.argsFactory.fromBackStackEntry(backStackEntry)
         CompositionLocalProvider(compositionLocalOf { pro }) {
             content.invoke()
         }*/
    private val InitTemplate = "%T<%T> {\n" +
            "    error(%S)\n" +
            "}"
}