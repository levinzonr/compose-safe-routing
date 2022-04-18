package cz.levinzonr.saferoute.processor.subprocessors

import cz.levinzonr.saferoute.annotations.Route
import cz.levinzonr.saferoute.annotations.RouteArg
import cz.levinzonr.saferoute.annotations.RouteArgType
import com.levinzonr.saferoute.codegen.models.ArgumentData
import com.levinzonr.saferoute.codegen.models.ArgumentType
import com.levinzonr.saferoute.codegen.models.OptionalArgData
import com.levinzonr.saferoute.codegen.models.RouteData
import cz.levinzonr.saferoute.processor.extensions.fieldByName
import java.lang.reflect.InvocationTargetException
import javax.lang.model.element.Element
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.TypeMirror
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import javax.lang.model.type.ExecutableType


internal class RouteDataBuilder(val packageName: String) {

    fun fromRepeatable(annotation: Annotation, annotatedElement: Element) : List<RouteData> {
        val annotations = annotation.fieldByName<Array<Annotation>>("value")
        return annotations.map { from(it, annotatedElement) }
    }

    fun from(annotation: Annotation, annotatedElement: Element): RouteData {

        val executableType = annotatedElement.asType() as ExecutableType
        val parameters = executableType.parameterTypes
        val params = parameters.map { it.toString() }
        return if (annotation is Route) {
            val arguments = annotation.args.map { ArgumentDataBuilder().from(it) }
            RouteData(
                name = annotation.name.decapitalize(),
                arguments = arguments,
                packageName= packageName,
                deeplinks = listOf(),
                routeTransition = null,
                contentName = annotatedElement.simpleName.toString(),
                params = params,
                navGraphName = annotation.name,
                start = annotation.start
            )
        } else {
            val navGraph = annotation.fieldByName<Annotation>("navGraph")
            val argsData = annotation.fieldByName<Array<Annotation>>("args")
            val deeplinksData = annotation.fieldByName<Array<Annotation>>("deepLinks")
            val routeName = annotation.fieldByName<String>("name").takeIf { it != "@null" }
            RouteData(
                name = (routeName ?: annotatedElement.simpleName.toString()).decapitalize(),
                arguments = argsData.map { ArgumentDataBuilder().from(it) },
                packageName = packageName,
                deeplinks = deeplinksData.map { DeeplinkDataBuilder.build(it) },
                routeTransition = annotation.getClassProperty("transition").toString(),
                contentName = annotatedElement.simpleName.toString(),
                params = params,
                navGraphName = navGraph.fieldByName("name"),
                start = navGraph.fieldByName("start")
            )
        }
    }
}

internal class ArgumentDataBuilder {

    fun from(routeArg: RouteArg): ArgumentData {
        val argType = requireNotNull(ArgumentType.values().find { routeArg.type.clazz == it.clazz })
        return with(routeArg) {
            val optional =  OptionalArgData.build(
                type = argType,
                value = defaultValue,
                isNullable = routeArg.type == RouteArgType.StringNullableType,
                isOptional = isOptional,
                name = name
            )
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
            val optional = OptionalArgData.build(type, defaultValue, isNullable, isOptional, name)
            ArgumentData(name, type, optional, isNullable)
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