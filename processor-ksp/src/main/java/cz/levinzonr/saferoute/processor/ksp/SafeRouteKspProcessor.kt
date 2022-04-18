package cz.levinzonr.saferoute.processor.ksp

import com.google.devtools.ksp.processing.CodeGenerator
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

@OptIn(KotlinPoetKspPreview::class)
internal class SafeRouteKspProcessor(
    private val logger: Logger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {

    internal class TypeH(val resolver: Resolver) : TypeHelper {
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

        val processingComponent = ProcessingComponent(
            logger = logger,
            typeHelper = TypeH(resolver),
            dataProcessor = KspDataProcessor(elements, resolver),
            directory = File(resolver.getAllFiles().first().packageName.getQualifier()),
            writer = KspWriter(codeGenerator)
        )


        try {
            RoutesGenerationProcessor(processingComponent)
                .process()
        } catch (e: Throwable) {
            logger.log("Error processing routes: ${e.stackTraceToString()}")
        }



        return emptyList()
    }

}