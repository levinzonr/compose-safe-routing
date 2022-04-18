package cz.levinzonr.saferoute.processor.extensions


inline fun <reified T> Any.fieldByName(name: String): T {
    try {
        return this::class.members.find { it.name == name }?.call(this) as T
    } catch (e: Exception) {
        throw IllegalArgumentException("Error process $name member: ${e.stackTraceToString()}")
    }
}