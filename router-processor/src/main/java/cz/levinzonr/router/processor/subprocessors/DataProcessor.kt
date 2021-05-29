package cz.levinzonr.router.processor.subprocessors

import cz.levinzonr.router.annotations.Route
import cz.levinzonr.router.annotations.RouteArg
import cz.levinzonr.router.processor.extensions.toKotlinClass
import cz.levinzonr.router.processor.models.ArgumentData
import cz.levinzonr.router.processor.models.ModelData
import cz.levinzonr.router.processor.models.RouteData
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment

object DataProcessor {

    fun process(processingEnv: ProcessingEnvironment, environment: RoundEnvironment?) : ModelData? {
        var packageName: String = ""
        val routes = environment?.getElementsAnnotatedWith(Route::class.java)?.map { element ->
            packageName = processingEnv.elementUtils.getPackageOf(element).toString()
            val annotation = element.getAnnotation(Route::class.java)
            val arguments = element.enclosedElements.mapNotNull {
                val argAnnotation =
                    it.getAnnotation(RouteArg::class.java) ?: return@mapNotNull null
                ArgumentData(argAnnotation.name, it.asType().toKotlinClass())
            }
            RouteData(element.simpleName.toString(), annotation.path, arguments)
        }?.takeIf { it.isNotEmpty() } ?: return null

        return ModelData(packageName, routes)
    }
}