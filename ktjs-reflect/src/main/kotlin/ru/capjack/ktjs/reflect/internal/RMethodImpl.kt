package ru.capjack.ktjs.reflect.internal

import ru.capjack.ktjs.reflect.RMethod
import ru.capjack.ktjs.reflect.RParameter
import kotlin.reflect.KClass

class RMethodImpl<T : Any, R : Any>(
	private val identifier: String,
	override val name: String,
	override val parameters: List<RParameter>,
	override val returnType: KClass<R>,
	override val annotations: List<Annotation>
) : RMethod<T, R> {
	
	override fun invoke(target: T, vararg arguments: Any): R {
		if (arguments.size != parameters.size) {
			throw IllegalArgumentException("Illegal arguments size (required: ${parameters.size}, given: ${arguments.size})")
		}
		
		return target.asDynamic()[identifier].apply(target, arguments).unsafeCast<R>()
	}
	
	override fun invoke(target: T, arguments: List<Any>): R {
		return invoke(target, arguments.toTypedArray())
	}
	
	override fun invoke(target: T, arguments: Map<String, Any>): R {
		return invoke(target, parameters.map { arguments[it.name] })
	}
}