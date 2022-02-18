package cz.levinzonr.saferoute.processor.subprocessors

import com.squareup.kotlinpoet.ClassName
import cz.levinzonr.saferoute.annotations.Route
import cz.levinzonr.saferoute.annotations.RouteArg
import cz.levinzonr.saferoute.annotations.RouteArgType
import cz.levinzonr.saferoute.processor.constants.Constants
import cz.levinzonr.saferoute.processor.extensions.fieldByName
import cz.levinzonr.saferoute.processor.models.ArgumentData
import cz.levinzonr.saferoute.processor.models.ArgumentType
import cz.levinzonr.saferoute.processor.models.OptionalArgData
import cz.levinzonr.saferoute.processor.models.RouteData
import java.lang.IllegalArgumentException
import java.lang.reflect.InvocationTargetException
import javax.lang.model.element.Element
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.TypeMirror
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import javax.lang.model.type.ExecutableType


internal class RouteDataBuilder(val packageName: String) {


    fun from(annotation: Annotation, annotatedElement: Element): RouteData {

        val executableType = annotatedElement.asType() as ExecutableType
        val parameters = executableType.parameterTypes
        val params = parameters.map { it.toString() }
        return if (annotation is Route) {
            val arguments = annotation.args.map { ArgumentDataBuilder().from(it) }
            RouteData(
                name = annotation.name.decapitalize(),
                arguments = arguments,
                packageName= packageName + "." + Constants.FILE_ARGS_DIR,
                deeplinks = listOf(),
                routeTransition = null,
                contentClassName = ClassName(packageName, annotatedElement.simpleName.toString()),
                params = params,
                navGraphName = annotation.name,
                start = annotation.start
            )
        } else {
            // TODO add navGraph support
            // val navGraph = annotation.fieldByName<Annotation>("navGraph")

            val argsData = annotation.fieldByName<Array<Annotation>>("args")
            val deeplinksData = annotation.fieldByName<Array<Annotation>>("deepLinks")
            RouteData(
                name = annotation.fieldByName<String>("name").decapitalize(),
                arguments = argsData.map { ArgumentDataBuilder().from(it) },
                packageName = packageName + "." + Constants.FILE_ARGS_DIR,
                deeplinks = deeplinksData.map { DeeplinkDataBuilder.build(it) },
                routeTransition = annotation.getClassProperty("transition"),
                contentClassName = ClassName(packageName, annotatedElement.simpleName.toString()),
                params = params,
                navGraphName = "TODO",
                start = false
            )
        }
    }
}

internal class ArgumentDataBuilder {

    fun from(routeArg: RouteArg): ArgumentData {
        val argType = requireNotNull(ArgumentType.values().find { routeArg.type.clazz == it.clazz })
        return with(routeArg) {
            val optional = if (isOptional) buildOptionData(
                argType,
                defaultValue,
                routeArg.type == RouteArgType.StringNullableType
            ) else null
            ArgumentData(name, argType, optional, type == RouteArgType.StringNullableType)
        }
    }

    fun from(annotation: Annotation): ArgumentData {
        return with(annotation) {
            val isOptional = fieldByName<Boolean>("isOptional")
            val defaultValue = fieldByName<String>("defaultValue")
            val name = fieldByName<String>("name")
            val type = ArgumentType.from(getClassProperty("type"))
            val isNullable = fieldByName<Boolean>("isNullable")
            val optional = if (isOptional) buildOptionData(type, defaultValue, isNullable) else null
            ArgumentData(name, type, optional, isNullable)
        }
    }

    private fun buildOptionData(
        type: ArgumentType,
        value: String,
        isNullable: Boolean
    ): OptionalArgData<*> {
        when (type) {
            ArgumentType.StringType -> {
                val stringDefault = if (isNullable) {
                    if (value == RouteArg.VALUE_NULL) null else value
                } else {
                    require(value != RouteArg.VALUE_NULL)
                    value
                }

                return OptionalArgData.OptionalString(stringDefault)
            }

            ArgumentType.IntType -> {
                val intValue =
                    requireNotNull(value.toIntOrNull()) { "Provided arg value ($value) is not matching type $type" }
                return OptionalArgData.OptionalInt(intValue)
            }
            ArgumentType.FloatType -> {
                val floatValue =
                    requireNotNull(value.toFloatOrNull()) { "Provided arg value ($value) is not matching type $type" }
                return OptionalArgData.OptionalFloat(floatValue)

            }
            ArgumentType.LongType -> {
                val longValue =
                    requireNotNull(value.toLongOrNull()) { "Provided arg value ($value) is not matching type $type" }
                return OptionalArgData.OptionalLong(longValue)
            }
            ArgumentType.BooleanType -> {
                val boolValue =
                    requireNotNull(value.toBoolean()) { "Provided arg value ($value) is not matching type $type" }
                return OptionalArgData.OptionalBool(boolValue)
            }
            else -> {
                throw IllegalArgumentException("Type $type is not supported")
            }
        }
    }
}


fun Any.getClassProperty(propertyName: String): TypeMirror {
    return try {
        val prop = this::class.memberProperties.first { it.name == propertyName }
        prop.call(this) as KClass<*>
        throw Exception("Expected InvocationTargetException")
    } catch (e: InvocationTargetException) {
        val cause = requireNotNull(e.cause as? MirroredTypeException)
        cause.typeMirror
    }

}