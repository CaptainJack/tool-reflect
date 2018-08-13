package ru.capjack.gradle.ktjs.reflect

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

interface RClass<T : Any> : KAnnotatedElement {
	val kClass: KClass<T>
	
	val constructorParameters: List<RParameter>
	
	val methods: Collection<RMethod<T, out Any>>
	
	val isInterface: Boolean
	
	fun createInstance(vararg arguments: Any): T
	
	fun createInstance(arguments: List<Any>): T
	
	fun createInstance(arguments: Map<String, Any>): T
}

