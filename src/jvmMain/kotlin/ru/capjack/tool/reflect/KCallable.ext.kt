@file:Suppress("NOTHING_TO_INLINE")

package ru.capjack.tool.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.full.valueParameters

actual inline val KCallable<*>.valueParameters: List<KParameter>
	get() = valueParameters

actual inline val KCallable<*>.returnTypeRef: KType
	get() = returnType

actual inline fun <T> KCallable<T>.callRef(vararg args: Any?): T {
	return call(*args)
}
