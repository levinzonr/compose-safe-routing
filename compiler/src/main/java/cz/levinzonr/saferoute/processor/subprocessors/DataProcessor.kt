package cz.levinzonr.saferoute.processor.subprocessors

import cz.levinzonr.saferoute.annotations.Route
import cz.levinzonr.saferoute.processor.constants.Constants
import cz.levinzonr.saferoute.processor.models.ModelData
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element

internal object DataProcessor {
    val RouteV2Class = Class.forName(Constants.ROUTE).asSubclass(Annotation::class.java)
    val RouteV1Class = Route::class.java
    fun process(processingEnv: ProcessingEnvironment, environment: RoundEnvironment?) : ModelData? {
        try {
            var packageName: String = ""
            val routes = environment?.getElementsAnnotatedWithAny(setOf(RouteV1Class, RouteV2Class))?.map { element ->
                packageName = processingEnv.elementUtils.getPackageOf(element).toString()
                val annotation = element.getAnnotation()
                RouteDataBuilder(packageName).from(annotation, element)
            }?.takeIf { it.isNotEmpty() } ?: return null

            return ModelData(packageName, routes)
        } catch (e: Exception) {
            throw Exception("Error while processing annotations: ${e.stackTraceToString()}")
        }
    }

    private fun Element.getAnnotation() : Annotation {
        return getAnnotation(RouteV1Class) ?: getAnnotation(RouteV2Class)
    }
}