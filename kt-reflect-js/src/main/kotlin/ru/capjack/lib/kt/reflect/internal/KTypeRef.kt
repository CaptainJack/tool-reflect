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
}
