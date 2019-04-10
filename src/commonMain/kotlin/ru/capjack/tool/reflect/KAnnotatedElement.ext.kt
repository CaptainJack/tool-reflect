package ru.capjack.tool.reflect

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

expect val KAnnotatedElement.annotationsRef: List<Annotation>

expect fun <T : Annotation> KAnnotatedElement.findAnnotation(clazz: KClass<T>): T?