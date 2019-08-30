package ru.capjack.tool.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

expect val KClass<*>.qualifiedNameRef: String?

expect val <T : Any> KClass<T>.primaryConstructor: KFunction<T>?

expect val KClass<*>.isInterface: Boolean

expect val KClass<*>.publicDeclaredMembers: Collection<KCallable<*>>

expect fun KClass<*>.getSupertypesWithAnnotation(clazz: KClass<out Annotation>): List<KClass<*>>

expect fun KClass<*>.isSubclassOf(base: KClass<*>): Boolean