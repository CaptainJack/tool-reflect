package ru.capjack.kt.reflect.internal

internal class JsConstructor<T>(
	name: String,
	returnType: KTypeRef,
	valueParameters: List<KParameterRef>,
	annotations: List<Annotation>,
	private val creator: dynamic
) : KFunctionRef<T>(name, returnType, "", valueParameters, annotations) {
	
	override fun doCall(args: Array<*>): T {
		return creator(args).unsafeCast<T>()
	}
}