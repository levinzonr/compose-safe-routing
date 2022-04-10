package cz.levinzonr.saferoute.processor.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.core.Logger
import com.levinzonr.saferoute.codegen.core.ProcessingComponents
import com.levinzonr.saferoute.codegen.core.TypeHelper
import java.io.File
import kotlin.math.log

class SafeRouteKspProcessor(
    private val logger: Logger,
    private val packageName: String,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {

    internal class TypeH(val resolver: Resolver): TypeHelper {
        override fun superTypes(value: Any?): List<String> {
            return emptyList()
        }
    }


    private val supportedAnnotations = listOf(
        Constants.ROUTE,
        Constants.ROUTES
    )

    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.log("started")
        val routeAnnotations = resolver.getSymbolsWithAnnotation(supportedAnnotations.first())
        val routesAnnotations = resolver.getSymbolsWithAnnotation(supportedAnnotations[1])
        logger.log(routeAnnotations.joinToString())
        logger.log(routeAnnotations.joinToString())
        val elements = listOf(routeAnnotations.toList(), routesAnnotations.toList()).flatten()

        val processingComponents = ProcessingComponents(
            logger = logger,
            typeHelper = TypeH(resolver),
            dataProcessor = KspDataProcessor(elements, packageName),
            directory = File(resolver.getAllFiles().first().filePath)
        )

        return emptyList()
    }


    private fun processElements(elements: List<KSAnnotated>) {
        elements.forEach { element ->
            val annotations = element.annotations
            annotations.forEach { annotation ->

            }
        }
    }


}