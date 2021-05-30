package cz.levinzonr.router.annotations

import kotlin.reflect.KClass


annotation class RouteArg(val name: String, val type: KClass<*>)