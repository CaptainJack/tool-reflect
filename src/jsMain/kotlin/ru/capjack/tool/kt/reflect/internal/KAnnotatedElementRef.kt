package ru.capjack.tool.kt.reflect.internal

import kotlin.reflect.KAnnotatedElement

internal abstract class KAnnotatedElementRef(
	override val annotations: List<Annotation>
) : KAnnotatedElement {
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is KAnnotatedElementRef) return false
		
		if (annotations != other.annotations) return false
		
		return true
	}
	
	override fun hashCode(): Int {
		return annotations.hashCode()
	}
}