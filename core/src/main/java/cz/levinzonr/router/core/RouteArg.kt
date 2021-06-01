package cz.levinzonr.router.core

import kotlin.reflect.KClass


annotation class RouteArg(val name: String, val type: KClass<*>)