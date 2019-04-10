package ru.capjack.tool.reflect

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

inline fun <reified T : Annotation> KAnnotatedElement.findAnnotation() =
	findAnnotation(T::class)

fun KAnnotatedElement.hasAnnotation(clazz: KClass<out Annotation>) =
	findAnnotation(clazz) != null

inline fun <reified T : Annotation> KAnnotatedElement.hasAnnotation() =
	hasAnnotation(T::class)

inline fun <reified T : Annotation> KClass<*>.getSupertypesWithAnnotation() =
	getSupertypesWithAnnotation(T::class)