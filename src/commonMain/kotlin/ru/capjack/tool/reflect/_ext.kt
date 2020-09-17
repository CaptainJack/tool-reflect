package ru.capjack.tool.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KClass


inline fun <reified T : Annotation> KClass<*>.getSupertypesWithAnnotation() = getSupertypesWithAnnotation(T::class)

fun KClass<*>.hasAnnotation(clazz: KClass<out Annotation>) = findAnnotation(clazz) != null

inline fun <reified T : Annotation> KClass<*>.hasAnnotation() = hasAnnotation(T::class)

inline fun <reified T : Annotation> KClass<*>.findAnnotation() = findAnnotation(T::class)



fun KParameter.hasAnnotation(clazz: KClass<out Annotation>) = findAnnotation(clazz) != null

inline fun <reified T : Annotation> KParameter.hasAnnotation() = hasAnnotation(T::class)

inline fun <reified T : Annotation> KParameter.findAnnotation() = findAnnotation(T::class)



fun KCallable<*>.hasAnnotation(clazz: KClass<out Annotation>) = findAnnotation(clazz) != null

inline fun <reified T : Annotation> KCallable<*>.hasAnnotation() = hasAnnotation(T::class)

inline fun <reified T : Annotation> KCallable<*>.findAnnotation() = findAnnotation(T::class)
