package cz.levinzonr.router.processor

import cz.levinzonr.router.annotations.Route
import cz.levinzonr.router.processor.codegen.RouteArgsBuilder
import cz.levinzonr.router.processor.subprocessors.DataProcessor
import cz.levinzonr.router.processor.subprocessors.RoutesActionsProcessor
import cz.levinzonr.router.processor.subprocessors.RoutesArgsProcessor
import cz.levinzonr.router.processor.subprocessors.RoutesProcessor
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import kotlin.Exception


@SupportedSourceVersion(SourceVersion.RELEASE_8)
class RouteProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }


    override fun getSupportedAnnotationTypes(): MutableSet<String> = mutableSetOf(
        Route::class.java.canonicalName
    )

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        try {
            val buildDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: return false
            val data = DataProcessor.process(processingEnv, roundEnv) ?: return false

            log("Data obtained, start actions processing, $data")
            RoutesActionsProcessor.process(data ,File(buildDir))

            log("Star args processing")
            RoutesArgsProcessor.process(data, File(buildDir))


            log("Start routes processings")
            RoutesProcessor.process(data, File(buildDir))

            return true

        } catch (e: Exception) {
            log(e.message, Diagnostic.Kind.ERROR)
            return false
        }

    }


    private fun log(message: String?, kind: Diagnostic.Kind = Diagnostic.Kind.NOTE) {
        processingEnv.messager.printMessage(kind, message)
    }
}