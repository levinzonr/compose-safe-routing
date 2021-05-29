package cz.levinzonr.router.processor

import com.squareup.kotlinpoet.FileSpec
import cz.levinzonr.router.processor.models.ArgumentData
import cz.levinzonr.router.processor.models.RouteData
import cz.levinzonr.router.annotations.Route
import cz.levinzonr.router.annotations.RouteArg
import cz.levinzonr.router.processor.codegen.RoutesBuilder
import cz.levinzonr.router.processor.models.ModelData
import java.io.File
import java.lang.Exception
import java.lang.IllegalStateException
import java.nio.file.Files
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic


@SupportedSourceVersion(SourceVersion.RELEASE_8)
class RouteProcessor : AbstractProcessor() {

    var i = 0

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }


    override fun getSupportedAnnotationTypes(): MutableSet<String> = mutableSetOf(
        Route::class.java.canonicalName
    )

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {

        val buildDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: return false

        val data = try {
            getRouteDataFrom(roundEnv) ?: return false
        } catch (e: Exception) {
            processingEnv.messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Error Getting data: ${e.message}"
            )
            return false
        }

        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "data: $data")
        return try {
            FileSpec.builder(data.packageName, Constants.FILE_ACTIONS)
                .addType(RoutesBuilder(data.routes).build())
                .build()
                .writeTo(File(buildDir))
            true
        } catch (e: Exception) {
            processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Error writing to file: ${e.message}")
            false
        }
    }


    private fun getRouteDataFrom(environment: RoundEnvironment?): ModelData? {
        try {
            var packageName: String = ""
            val routes = environment?.getElementsAnnotatedWith(Route::class.java)?.map { element ->
                packageName = processingEnv.elementUtils.getPackageOf(element).toString()
                val annotation = element.getAnnotation(Route::class.java)
                val arguments = element.enclosedElements.mapNotNull {
                    val argAnnotation =
                        it.getAnnotation(RouteArg::class.java) ?: return@mapNotNull null
                    ArgumentData(argAnnotation.name, it.asType().type())
                }
                RouteData(element.simpleName.toString(), annotation.path, arguments)
            }?.takeIf { it.isNotEmpty() } ?: return null

            return ModelData(packageName, routes)
        } catch (e: Exception) {
            processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, e.localizedMessage)
            throw e
        }
    }

    private fun TypeMirror.type() : String {
        return if (kind.isPrimitive) {
            Int::class.javaObjectType.canonicalName
        } else {
             toString()
        }
    }

}