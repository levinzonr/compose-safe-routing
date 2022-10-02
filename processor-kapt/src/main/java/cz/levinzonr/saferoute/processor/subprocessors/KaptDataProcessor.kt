package cz.levinzonr.saferoute.processor.subprocessors

import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.core.DataProcessor
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.ModelDataBuilder
import com.levinzonr.saferoute.codegen.models.NavGraphData
import com.levinzonr.saferoute.codegen.models.RouteData
import cz.levinzonr.saferoute.annotations.Route
import cz.levinzonr.saferoute.processor.extensions.fieldByName
import java.lang.IllegalArgumentException
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element

internal class KaptDataProcessor(
    private val processingEnv: ProcessingEnvironment,
    private val environment: RoundEnvironment?
) : DataProcessor {
    private val RouteV2Class = Class.forName(Constants.ROUTE).asSubclass(Annotation::class.java)
    private val Graphs = Class.forName(Constants.NAV_GRAPH).asSubclass(Annotation::class.java)
    private val RouteV1Class = Route::class.java

    private fun supported(): Set<Class<out Annotation>> {
        return setOf(RouteV1Class, RouteV2Class)
    }

    override fun process(): ModelData? {
        try {
            val dataBuilder = ModelDataBuilder()
            val graphs = environment?.getElementsAnnotatedWith(Graphs).orEmpty()

            graphs.forEach {
                dataBuilder.addGraph(
                    name = it.simpleName.toString(),
                    packageName = processingEnv.elementUtils.getPackageOf(it).toString(),
                    source = null
                )
            }

            val elements =
                requireNotNull(environment?.getElementsAnnotatedWithAny(supported())) { "eror " }
            val routes = elements.map {
                val route = processingEnv.processAnnotation(it)
                dataBuilder.addRoute(route, graphs = it.findGraphs(dataBuilder.graphs))
                route
            }

            val packageName =
                processingEnv.options[Constants.ARG_PACKAGE_NAME] ?: routes.first().packageName
            return dataBuilder.build(packageName)
        } catch (e: Exception) {
            throw Exception("Error while processing annotations: ${e.stackTraceToString()}")
        }
    }

    private fun Element.findGraphs(graphs: List<ModelDataBuilder.Graph>): List<ModelDataBuilder.RouteGraph> {

        val graphElements = annotationMirrors.filter { mirror ->
            graphs.any {  mirror.annotationType.toString().contains(it.name) }
        }

        if (graphElements.isEmpty()) return emptyList()

        return graphElements.map {
            ModelDataBuilder.RouteGraph(
                name = it.annotationType.asElement().simpleName.toString(),
                start = it.elementValues.firstNotNullOfOrNull { it.key.toString().contains("start") } ?: false
            )
        }

    }

    private fun ProcessingEnvironment.processAnnotation(element: Element): RouteData {
        val packageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation()
        return RouteDataBuilder(packageName).from(annotation, element)
    }

    private fun Element.getAnnotation(): Annotation {
        return getAnnotation(RouteV1Class) ?: getAnnotation(RouteV2Class)
    }
}
