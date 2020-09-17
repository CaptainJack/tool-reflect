package ru.capjack.tool.reflect

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

internal fun <T : Annotation> KAnnotatedElement.findAnnotation0(clazz: KClass<T>): T? {
	@Suppress("UNCHECKED_CAST")
	return annotations.find { clazz.isInstance(it) } as T?
}