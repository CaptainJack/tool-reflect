package ru.capjack.tool.kt.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KParameter
import kotlin.reflect.KType

expect val KCallable<*>.valueParameters: List<KParameter>

expect val KCallable<*>.returnTypeRef: KType

expect fun <T> KCallable<T>.callRef(vararg args: Any?): T
