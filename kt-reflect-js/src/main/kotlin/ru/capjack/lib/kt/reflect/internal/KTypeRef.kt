package ru.capjack.lib.kt.reflect.internal

import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection

internal class KTypeRef(
	val kClass: KClass<*>,
	annotations: List<Annotation>
) : KAnnotatedElementRef(annotations), KType {
	override val arguments: List<KTypeProjection>
		get() = throwNotImplemented()
	
	override val classifier: KClassifier?
		get() = kClass
	
	override val isMarkedNullable: Boolean
		get() = throwNotImplemented()
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is KTypeRef) return false
		if (!super.equals(other)) return false
		
		if (kClass != other.kClass) return false
		
		return true
	}
	
	override fun hashCode(): Int {
		var result = super.hashCode()
		result = 31 * result + kClass.hashCode()
		return result
	}
}
