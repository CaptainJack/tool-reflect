package ru.capjack.tool.reflect.internal

import kotlin.reflect.KParameter

internal class KParameterRef(
	override val index: Int,
	override val name: String?,
	override val type: KTypeRef,
	annotations: List<Annotation>

) : KAnnotatedElementRef(annotations), KParameter {
	
	override val isOptional: Boolean
		get() = throwNotImplemented()
	
	override val isVararg: Boolean
		get() = throwNotImplemented()
	
	override val kind: KParameter.Kind
		get() = throwNotImplemented()
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is KParameterRef) return false
		if (!super.equals(other)) return false
		
		if (index != other.index) return false
		if (name != other.name) return false
		if (type != other.type) return false
		
		return true
	}
	
	override fun hashCode(): Int {
		var result = super.hashCode()
		result = 31 * result + index
		result = 31 * result + (name?.hashCode() ?: 0)
		result = 31 * result + type.hashCode()
		return result
	}
}