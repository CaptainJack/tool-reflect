package ru.capjack.ktjs.reflect

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

interface RMethod<T : Any, R : Any> : KAnnotatedElement {
	val name: String
	val parameters: List<RParameter>
	val returnType: KClass<R>
	
	fun invoke(target: T, vararg arguments: Any): R
	
	fun invoke(target: T, arguments: List<Any>): R
	
	fun invoke(target: T, arguments: Map<String, Any>): R
}