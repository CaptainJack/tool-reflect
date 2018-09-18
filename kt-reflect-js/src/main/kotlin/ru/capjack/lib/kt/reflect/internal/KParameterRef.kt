package ru.capjack.lib.kt.reflect.internal

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
}