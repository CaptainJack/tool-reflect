package ru.capjack.lib.kt.reflect

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

actual fun <T : Annotation> KAnnotatedElement.findAnnotation(clazz: KClass<T>): T? {
	@Suppress("UNCHECKED_CAST")
	return annotations.find { clazz.isInstance(it) } as T?
}