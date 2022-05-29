package cz.levinzonr.saferoute.processor.subprocessors

import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.core.DataProcessor
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.NavGraphData
import com.levinzonr.saferoute.codegen.models.RouteData
import cz.levinzonr.saferoute.annotations.Route
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element

internal class KaptDataProcessor(
    private val processingEnv: ProcessingEnvironment,
    private val environment: RoundEnvironment?
) : DataProcessor {
    private val RouteV2Class = Class.forName(Constants.ROUTE).asSubclass(Annotation::class.java)
    private val Routes = Class.forName(Constants.ROUTES).asSubclass(Annotation::class.java)
    private val RouteV1Class = Route::class.java

    private fun supported(): Set<Class<out Annotation>> {
        return setOf(RouteV1Class, RouteV2Class, Routes)
    }

    override fun process(): ModelData? {
        try {
            val packageName = processingEnv.options[Constants.ARG_PACKAGE_NAME]
            val elements = requireNotNull(environment?.getElementsAnnotatedWithAny(supported())) { "eror " }
            val routes = elements
                .map { processingEnv.processAnnotations(it) }
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

            return ModelData(packageName ?: routes.first().packageName, graphs)
        } catch (e: Exception) {
            throw Exception("Error while processing annotations: ${e.stackTraceToString()}")
        }
    }

    private fun ProcessingEnvironment.processAnnotation(element: Element): RouteData {
        val packageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation()
        return RouteDataBuilder(packageName).from(annotation, element)
    }

    private fun ProcessingEnvironment.processAnnotations(element: Element): List<RouteData> {
        val packageName = elementUtils.getPackageOf(element).toString()
        val routesAnnotaitons = element.getAnnotation(Routes) ?: return listOf(processAnnotation(element))
        return RouteDataBuilder(packageName).fromRepeatable(routesAnnotaitons, element)
    }

    private fun Element.getAnnotation(): Annotation {
        return getAnnotation(RouteV1Class) ?: getAnnotation(RouteV2Class)
    }
}
