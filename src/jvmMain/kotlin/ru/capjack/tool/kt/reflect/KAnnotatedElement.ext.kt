package ru.capjack.tool.kt.reflect

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

actual inline val KAnnotatedElement.annotationsRef: List<Annotation>
	get() = annotations

actual fun <T : Annotation> KAnnotatedElement.findAnnotation(clazz: KClass<T>): T? {
	@Suppress("UNCHECKED_CAST")
	return annotations.find { clazz.isInstance(it) } as T?
}