package cz.levinzonr.saferoute.processor

import cz.levinzonr.saferoute.annotations.Route
import cz.levinzonr.saferoute.processor.constants.Constants
import cz.levinzonr.saferoute.processor.logger.KaptLogger
import cz.levinzonr.saferoute.processor.subprocessors.DataProcessor
import cz.levinzonr.saferoute.processor.subprocessors.RoutesActionsProcessor
import cz.levinzonr.saferoute.processor.subprocessors.RoutesArgsProcessor
import cz.levinzonr.saferoute.processor.subprocessors.RoutesProcessor
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
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

        try {
            val buildDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: return false

            val data = DataProcessor.process(processingEnv, roundEnv) ?: return false

            logger.log("Data obtained, start actions processing, $data")
            RoutesActionsProcessor.process(data ,File(buildDir))

            logger.log("Star args processing")
            RoutesArgsProcessor.process(data, File(buildDir))


            logger.log("Start routes processings")
            RoutesProcessor(processingEnv, logger).process(data, File(buildDir))

            logger.log("Routes processings complete")
            return true

        } catch (e: Exception) {
            logger.log(e.message.toString())
            return false
        }

    }

}