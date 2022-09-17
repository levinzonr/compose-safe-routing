package cz.levinzonr.saferoute.processor.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.core.LogLevel
import com.levinzonr.saferoute.codegen.core.Logger
import com.levinzonr.saferoute.codegen.core.ProcessingComponent
import com.levinzonr.saferoute.codegen.core.RoutesGenerationProcessor
import com.levinzonr.saferoute.codegen.core.TypeHelper
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import java.io.File
import kotlin.math.log

@OptIn(KotlinPoetKspPreview::class)
internal class SafeRouteKspProcessor(
    private val logger: Logger,
    private val options: Map<String, String>,
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
        Constants.ROUTES,
        Constants.NAV_GRAPH
    )

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val graphs = resolver.getSymbolsWithAnnotation(supportedAnnotations[2]).toList()
        val routeAnnotations = resolver.getSymbolsWithAnnotation(supportedAnnotations.first())
        val routesAnnotations = resolver.getSymbolsWithAnnotation(supportedAnnotations[1])

        val elements = listOf(routeAnnotations.toList(), routesAnnotations.toList())
            .flatten()
            .filterIsInstance<KSFunctionDeclaration>()

        logger.log("Graphs: $graphs", level = LogLevel.Warning)

        if (elements.isEmpty() && graphs.isEmpty()) return emptyList()

        val packageName =
            options[Constants.ARG_PACKAGE_NAME] ?: elements.first().packageName.asString()
        val processingComponent = ProcessingComponent(
            logger = logger,
            typeHelper = KspTypeHelper(logger),
            dataProcessor = KspDataProcessor(graphs, elements, resolver, packageName, logger),
            directory = File(packageName),
            writer = KspWriter(codeGenerator, resolver),
        )

        logger.log("Log", level = LogLevel.Warning)

        try {
            RoutesGenerationProcessor(processingComponent)
                .process()
        } catch (e: Throwable) {
            logger.log("Error processing routes: ${e.stackTraceToString()}", level = LogLevel.Error)
        }

        return emptyList()
    }
}
