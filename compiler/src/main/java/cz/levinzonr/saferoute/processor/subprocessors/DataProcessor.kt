package cz.levinzonr.saferoute.processor.subprocessors

import cz.levinzonr.saferoute.annotations.Route
import cz.levinzonr.saferoute.processor.constants.Constants
import cz.levinzonr.saferoute.processor.logger.KaptLogger
import cz.levinzonr.saferoute.processor.models.ModelData
import cz.levinzonr.saferoute.processor.models.NavGraphData
import cz.levinzonr.saferoute.processor.models.RouteData
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

internal object DataProcessor {
    val RouteV2Class = Class.forName(Constants.ROUTE).asSubclass(Annotation::class.java)
    val Routes = Class.forName(Constants.ROUTES).asSubclass(Annotation::class.java)
    val RouteV1Class = Route::class.java

    private fun supported(): Set<Class<out Annotation>> {
        return setOf(RouteV1Class, RouteV2Class, Routes)
    }

    fun process(processingEnv: ProcessingEnvironment, environment: RoundEnvironment?): ModelData? {
        try {

            var packageName = ""
            val elements = requireNotNull(environment?.getElementsAnnotatedWithAny(supported())) { "eror "}
            val routes = elements
                .map {
                    packageName = processingEnv.elementUtils.getPackageOf(it).toString()
                    processingEnv.processAnnotations(it)
                }
                .flatten()
                .takeIf { it.isNotEmpty() } ?: return null


            val graphs = routes.groupBy { it.navGraphName }.map { entry ->
                val startDestination = entry.value.find { it.start }
                NavGraphData(
                    name = entry.key,
                    routes = entry.value,
                    start = requireNotNull(startDestination) { "NavGraph [${entry.key}] has no start route specified" }
                )
            }

            return ModelData(packageName, graphs)
        } catch (e: Exception) {
            throw Exception("Error while processing annotations: ${e.stackTraceToString()}")
        }
    }

    private fun ProcessingEnvironment.processAnnotation(element: Element): RouteData {
        val packageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation()
        return RouteDataBuilder(packageName).from(annotation, element)
    }


    private fun ProcessingEnvironment.processAnnotations(element: Element) : List<RouteData> {
        val packageName = elementUtils.getPackageOf(element).toString()
        val routesAnnotaitons = element.getAnnotation(Routes) ?: return listOf(processAnnotation(element))
        return RouteDataBuilder(packageName).fromRepeatable(routesAnnotaitons, element)
    }

    private fun Element.getAnnotation(): Annotation {
        return getAnnotation(RouteV1Class) ?: getAnnotation(RouteV2Class)
    }
}