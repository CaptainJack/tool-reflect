package ru.capjack.lib.kt.reflect

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

expect fun <T : Annotation> KAnnotatedElement.findAnnotation(clazz: KClass<T>): T?