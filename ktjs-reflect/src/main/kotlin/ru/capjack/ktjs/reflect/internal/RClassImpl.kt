package ru.capjack.ktjs.reflect.internal

import ru.capjack.ktjs.reflect.RClass
import ru.capjack.ktjs.reflect.RMethod
import ru.capjack.ktjs.reflect.RParameter
import kotlin.reflect.KClass

@Suppress("UnsafeCastFromDynamic")
internal class RClassImpl<T : Any>(
	override val kClass: KClass<T>,
	metadata: dynamic
) : RClass<T> {
	override val annotations: List<Annotation>
	override val constructorParameters: List<RParameter>
	override val methods: Collection<RMethod<T, *>>
	override val isInterface: Boolean
	
	private var constructor: dynamic = null
	
	init {
		val reflect = metadata.reflect
			?: throw RuntimeException("Class $kClass is not reflected")
		
		isInterface = metadata.kind == js("Kotlin.Kind.INTERFACE")
		
		annotations = Metadata.fetchAnnotations(reflect.annotations)
		
		if (!isInterface && reflect.constructor) {
			constructorParameters = Metadata.fetchParameters(reflect.constructor[0])
			constructor = reflect.constructor[1]
		}
		else {
			constructorParameters = emptyList()
		}
		
		methods = Metadata.fetchMethods(reflect.methods)
	}
	
	override fun createInstance(vararg arguments: Any): T {
		if (constructor == null) {
			throw RuntimeException("Class $kClass is not constructable")
		}
		if (arguments.size != constructorParameters.size) {
			throw IllegalArgumentException("Illegal arguments size (required: ${constructorParameters.size}, given: ${arguments.size})")
		}
		
		return constructor.apply()
	}
	
	override fun createInstance(arguments: List<Any>): T {
		return createInstance(arguments.toTypedArray())
	}
	
	override fun createInstance(arguments: Map<String, Any>): T {
		return createInstance(constructorParameters.map { arguments[it.name] })
	}
	
}