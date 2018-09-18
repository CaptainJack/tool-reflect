package ru.capjack.lib.kt.reflect.internal

import kotlin.reflect.KCallable
import kotlin.reflect.KParameter
import kotlin.reflect.KTypeParameter
import kotlin.reflect.KVisibility

internal abstract class KCallableRef<out R>(
	override val name: String,
	override val returnType: KTypeRef,
	val accessName: String,
	val valueParameters: List<KParameterRef>,
	annotations: List<Annotation>
) : KAnnotatedElementRef(annotations), KCallable<R> {
	
	override val isAbstract: Boolean
		get() = throwNotImplemented()
	
	override val isFinal: Boolean
		get() = throwNotImplemented()
	
	override val isOpen: Boolean
		get() = throwNotImplemented()
	
	override val parameters: List<KParameter>
		get() = throwNotImplemented()
	
	override val typeParameters: List<KTypeParameter>
		get() = throwNotImplemented()
	
	override val visibility: KVisibility?
		get() = throwNotImplemented()
	
	override fun call(vararg args: Any?): R {
		return doCall(args)
	}
	
	override fun callBy(args: Map<KParameter, Any?>): R {
		throwNotImplemented()
	}
	
	abstract fun doCall(args: Array<*>): R
}
