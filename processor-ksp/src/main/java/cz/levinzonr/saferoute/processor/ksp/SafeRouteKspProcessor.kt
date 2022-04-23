package cz.levinzonr.saferoute.processor.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.*
import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.core.*
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import java.io.File
import java.lang.IllegalArgumentException
import kotlin.math.log

@OptIn(KotlinPoetKspPreview::class)
internal class SafeRouteKspProcessor(
    private val logger: Logger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {

    internal class KspTypeHelper(private val logger: Logger) : TypeHelper {
        override fun superTypes(value: Any?): List<String> {
            return if (value is KSClassDeclaration) {
                value.superTypes.map { it.resolve().declaration.qualifiedName!!.asString() }
                    .toList().also { logger.log(it.toString()) }
            } else {
                emptyList()
            }
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
            typeHelper = KspTypeHelper(logger),
            dataProcessor = KspDataProcessor(elements, resolver),
            directory = File(resolver.getAllFiles().first().packageName.getQualifier()),
            writer = KspWriter(codeGenerator, resolver)
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