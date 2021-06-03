package cz.levinzonr.router.processor.subprocessors

import cz.levinzonr.router.core.Route
import cz.levinzonr.router.core.RouteArg
import cz.levinzonr.router.core.RouteArgType
import cz.levinzonr.router.processor.models.ArgumentData
import cz.levinzonr.router.processor.models.ModelData
import cz.levinzonr.router.processor.models.OptionalArgData
import cz.levinzonr.router.processor.models.RouteData
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment

object DataProcessor {

    fun process(processingEnv: ProcessingEnvironment, environment: RoundEnvironment?) : ModelData? {
        try {
            var packageName: String = ""
            val routes = environment?.getElementsAnnotatedWith(Route::class.java)?.map { element ->

                packageName = processingEnv.elementUtils.getPackageOf(element).toString()
                val annotation = element.getAnnotation(Route::class.java)
                val arguments = annotation.args.map { it.toArgumentData()  }
                RouteData(annotation.name, arguments)
            }?.takeIf { it.isNotEmpty() } ?: return null

            return ModelData(packageName, routes)
        } catch (e: Exception) {
            throw Exception("Error while processing annotations: ${e.stackTraceToString()}")
        }
    }

    private fun RouteArg.toArgumentData() : ArgumentData {
        val optional = if (isOptional) buildOptionData(type, defaultValue) else null
        return ArgumentData(name, type.clazz, optional)
    }


    private fun buildOptionData(type: RouteArgType, value: String) : OptionalArgData<*> {
        when(type) {
            RouteArgType.ArgStringNonNull -> {
                require(value != RouteArg.VALUE_NULL)
                return OptionalArgData.OptionalString(value)
            }
            RouteArgType.ArgStringNullable -> {
                val value = if (value == RouteArg.VALUE_NULL) null else value
                return OptionalArgData.OptionalString(value)
            }
            RouteArgType.ArgInt -> {
                val intValue = requireNotNull(value.toIntOrNull()) { "Provided arg value ($value) is not matching type $type"}
                return OptionalArgData.OptionalInt(intValue)
            }
            RouteArgType.ArgFloat -> {
                val floatValue = requireNotNull(value.toFloatOrNull()) { "Provided arg value ($value) is not matching type $type"}
                return OptionalArgData.OptionalFloat(floatValue)

            }
            RouteArgType.ArgLong -> {
                val longValue = requireNotNull(value.toLongOrNull()) { "Provided arg value ($value) is not matching type $type"}
                return OptionalArgData.OptionalLong(longValue)
            }
            RouteArgType.ArgBool -> {
                val boolValue = requireNotNull(value.toBoolean()) { "Provided arg value ($value) is not matching type $type"}
                return OptionalArgData.OptionalBool(boolValue)
            }
        }
    }
}