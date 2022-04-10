package cz.levinzonr.saferoute.processor

import cz.levinzonr.saferoute.annotations.Route
import com.levinzonr.saferoute.codegen.constants.Constants
import com.levinzonr.saferoute.codegen.core.FileGenProcessor
import com.levinzonr.saferoute.codegen.core.LogLevel
import com.levinzonr.saferoute.codegen.core.ProcessingComponents
import com.levinzonr.saferoute.codegen.core.TypeHelper
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
import kotlin.math.log


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
        val processingComponents = ProcessingComponents(
            logger = logger,
            typeHelper = TypeHelperImpl(processingEnv.typeUtils)
        )

        try {
            val buildDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: return false
            val data = KaptDataProcessor(processingEnv, roundEnv).process() ?: return false
            FileGenProcessor(processingComponents).process(data, File(buildDir))
            return true
        } catch (e: Exception) {
            logger.log(e.stackTraceToString(), level = LogLevel.Error)
            return false
        }

    }

}