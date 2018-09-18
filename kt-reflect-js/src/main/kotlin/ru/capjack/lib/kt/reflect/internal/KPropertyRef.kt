package ru.capjack.lib.kt.reflect.internal

import kotlin.reflect.KProperty

internal class KPropertyRef<out R>(
	name: String,
	returnType: KTypeRef,
	accessName: String,
	valueParameters: List<KParameterRef>,
	annotations: List<Annotation>
) : KCallableRef<R>(name, returnType, accessName, valueParameters, annotations), KProperty<R> {
	
	override val isLateinit: Boolean
		get() = throwNotImplemented()
	
	override val isConst: Boolean
		get() = throwNotImplemented()
	
	override val getter: KProperty.Getter<R>
		get() = throwNotImplemented()
	
	override fun doCall(args: Array<*>): R {
		return args[0].asDynamic()[accessName].unsafeCast<R>()
	}
}