package cz.levinzonr.saferoute.processor.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.core.*
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.writeTo
import java.io.File
import java.lang.IllegalArgumentException
import kotlin.math.log

@OptIn(KotlinPoetKspPreview::class)
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
        val routeAnnotations = resolver.getSymbolsWithAnnotation(supportedAnnotations.first())
        val routesAnnotations = resolver.getSymbolsWithAnnotation(supportedAnnotations[1])

        val elements = listOf(routeAnnotations.toList(), routesAnnotations.toList()).flatten()
            .filterIsInstance<KSFunctionDeclaration>()

        if (elements.isEmpty()) return emptyList()

        val processingComponents = ProcessingComponents(
            logger = logger,
            typeHelper = TypeH(resolver),
            dataProcessor = KspDataProcessor(elements, resolver),
            directory = File(resolver.getAllFiles().first().packageName.getQualifier()),
            writer = object : Writer {
                override fun write(fileSpec: FileSpec, directory: File) {
                    fileSpec.writeTo(codeGenerator, aggregating = true)
                }
            }
        )


        try {
            RoutesGenerationProcessor(processingComponents)
                .process()
        } catch (e: Throwable) {
            logger.log("Error processing routes: ${e.stackTraceToString()}")
        }



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