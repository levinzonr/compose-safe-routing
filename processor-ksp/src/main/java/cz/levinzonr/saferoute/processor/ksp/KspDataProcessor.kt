package cz.levinzonr.saferoute.processor.ksp

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.core.DataProcessor
import com.levinzonr.saferoute.codegen.models.ModelData
import com.levinzonr.saferoute.codegen.models.NavGraphData
import com.levinzonr.saferoute.codegen.models.RouteData
import kotlin.reflect.KClass

class KspDataProcessor(
    private val elements: List<KSAnnotated>,
    private val packageName: String
) : DataProcessor {

    @OptIn(KspExperimental::class)
    override fun process(): ModelData? {
        val routes = elements.map { element ->
            element.annotations.map { it.process(element) }.toList()
        }.flatten()


        val graphs = routes.groupBy { it.navGraphName }.map {
            NavGraphData(
                name = it.key,
                routes = it.value,
                start = requireNotNull(it.value.find { it.start })
            )
        }

        return ModelData(
            packageName = packageName,
            navGraphs = graphs
        )
    }



    private fun KSAnnotation.process(element: KSAnnotated) : RouteData {
        return RouteData(
            name = fieldByName("name"),
            arguments = emptyList(),
            packageName = packageName,
            deeplinks = emptyList(),
            routeTransition = null,
            contentName = element.toString(),
            params = emptyList(),
            navGraphName = "main",
            start = true
        )
    }

}

inline fun<T> KSAnnotation.fieldByName(name: String) : T {
    return arguments.first { it.name?.asString() == name }.value as T
}