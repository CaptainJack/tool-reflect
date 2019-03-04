package ru.capjack.tool.kt.reflect

import ru.capjack.tool.kt.reflect.internal.ref
import kotlin.reflect.KCallable
import kotlin.reflect.KParameter
import kotlin.reflect.KType

actual val KCallable<*>.valueParameters: List<KParameter>
	get() = ref.valueParameters

actual val KCallable<*>.returnTypeRef: KType
	get() = ref.returnType

actual fun <T> KCallable<T>.callRef(vararg args: Any?): T {
	return ref.doCall(args)
}

val KCallable<*>.accessName: String
	get() = ref.accessName
