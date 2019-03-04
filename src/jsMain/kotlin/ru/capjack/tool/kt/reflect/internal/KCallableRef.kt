package ru.capjack.tool.kt.reflect.internal

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
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is KCallableRef<*>) return false
		if (!super.equals(other)) return false
		
		if (name != other.name) return false
		if (returnType != other.returnType) return false
		if (accessName != other.accessName) return false
		if (valueParameters != other.valueParameters) return false
		
		return true
	}
	
	override fun hashCode(): Int {
		var result = super.hashCode()
		result = 31 * result + name.hashCode()
		result = 31 * result + returnType.hashCode()
		result = 31 * result + accessName.hashCode()
		result = 31 * result + valueParameters.hashCode()
		return result
	}
}
