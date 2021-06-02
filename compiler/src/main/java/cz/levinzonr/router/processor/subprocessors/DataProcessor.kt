package cz.levinzonr.router.processor.subprocessors

import cz.levinzonr.router.core.Route
import cz.levinzonr.router.core.args.RouteBoolArg
import cz.levinzonr.router.core.args.RouteFloatArg
import cz.levinzonr.router.core.args.RouteIntArg
import cz.levinzonr.router.core.args.RouteLongArg
import cz.levinzonr.router.core.args.RouteStringArg
import cz.levinzonr.router.processor.models.ArgumentData
import cz.levinzonr.router.processor.models.ModelData
import cz.levinzonr.router.processor.models.RouteData
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.type.MirroredTypeException

object DataProcessor {

    fun process(processingEnv: ProcessingEnvironment, environment: RoundEnvironment?) : ModelData? {
        try {
            var packageName: String = ""
            val routes = environment?.getElementsAnnotatedWith(Route::class.java)?.map { element ->

                packageName = processingEnv.elementUtils.getPackageOf(element).toString()
                val annotation = element.getAnnotation(Route::class.java)
                val arguments = element.getArguments()
                RouteData(annotation.name, arguments)
            }?.takeIf { it.isNotEmpty() } ?: return null

            return ModelData(packageName, routes)
        } catch (e: Exception) {
            throw Exception("Error while processing annotations: ${e.stackTraceToString()}")
        }
    }

    private fun Element.getArguments() : List<ArgumentData> {
        val argsAnnotations = SUPPORTED_ARG_ANNOTATIONS.map { getAnnotationsByType(it).toList() }.flatten()
        return argsAnnotations.map {
            when(it) {
                is RouteIntArg -> it.toArgumentData()
                is RouteStringArg -> it.toArgumentData()
                is RouteLongArg -> it.toArgumentData()
                is RouteBoolArg -> it.toArgumentData()
                is RouteFloatArg -> it.toArgumentData()
                else -> throw IllegalArgumentException("Type $it not suppoerted")
            }
        }
    }

    private fun RouteIntArg.toArgumentData() : ArgumentData {
        return ArgumentData(
            name = name,
            type = Int::class
        )
    }

    private fun RouteStringArg.toArgumentData() : ArgumentData {
        return ArgumentData(
            name = name,
            type = String::class
        )
    }


    private fun RouteFloatArg.toArgumentData() : ArgumentData {
        return ArgumentData(
            name = name,
            type = Float::class
        )
    }

    private fun RouteBoolArg.toArgumentData() : ArgumentData {
        return ArgumentData(
            name = name,
            type = Boolean::class
        )
    }


    private fun RouteLongArg.toArgumentData() : ArgumentData {
        return ArgumentData(
            name = name,
            type = Long::class
        )
    }



    private val SUPPORTED_ARG_ANNOTATIONS = arrayListOf(
        RouteStringArg::class.java,
        RouteFloatArg::class.java,
        RouteBoolArg::class.java,
        RouteLongArg::class.java,
        RouteIntArg::class.java
    )

}