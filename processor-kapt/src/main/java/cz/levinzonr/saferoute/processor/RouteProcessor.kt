package cz.levinzonr.saferoute.processor

import cz.levinzonr.saferoute.annotations.Route
import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.core.DataProcessor
import com.levinzonr.saferoute.codegen.core.RoutesGenerationProcessor
import com.levinzonr.saferoute.codegen.core.LogLevel
import com.levinzonr.saferoute.codegen.core.ProcessingComponents
import cz.levinzonr.saferoute.processor.logger.KaptLogger
import cz.levinzonr.saferoute.processor.subprocessors.KaptDataProcessor
import cz.levinzonr.saferoute.processor.typehelper.TypeHelperImpl
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import kotlin.Exception


@SupportedSourceVersion(SourceVersion.RELEASE_8)
internal class RouteProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }


    override fun getSupportedAnnotationTypes(): MutableSet<String> = mutableSetOf(
        Route::class.java.canonicalName,
        Constants.ROUTE,
        Constants.ROUTES
    )

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {

        val logger = KaptLogger(processingEnv)
        val buildDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: return false

        val processingComponents = ProcessingComponents(
            logger = logger,
            typeHelper = TypeHelperImpl(processingEnv.typeUtils),
            dataProcessor = KaptDataProcessor(processingEnv, roundEnv),
            directory = File(buildDir)
        )
        return try {
            RoutesGenerationProcessor(processingComponents).process()
            true
        } catch (e: Exception) {
            logger.log(e.stackTraceToString(), level = LogLevel.Error)
            false
        }

    }

}