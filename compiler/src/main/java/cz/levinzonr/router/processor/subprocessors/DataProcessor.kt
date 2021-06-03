package cz.levinzonr.router.processor.subprocessors

import cz.levinzonr.router.core.Route
import cz.levinzonr.router.core.RouteArg
import cz.levinzonr.router.core.RouteArgType
import cz.levinzonr.router.processor.models.ArgumentData
import cz.levinzonr.router.processor.models.ModelData
import cz.levinzonr.router.processor.models.RouteData
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element

object DataProcessor {

    fun process(processingEnv: ProcessingEnvironment, environment: RoundEnvironment?) : ModelData? {
        try {
            var packageName: String = ""
            val routes = environment?.getElementsAnnotatedWith(Route::class.java)?.map { element ->

                packageName = processingEnv.elementUtils.getPackageOf(element).toString()
                val annotation = element.getAnnotation(Route::class.java)
                val arguments = annotation.args.map { ArgumentData(it.name, it.type.clazz)  }
                RouteData(annotation.name, arguments)
            }?.takeIf { it.isNotEmpty() } ?: return null

            return ModelData(packageName, routes)
        } catch (e: Exception) {
            throw Exception("Error while processing annotations: ${e.stackTraceToString()}")
        }
    }
}