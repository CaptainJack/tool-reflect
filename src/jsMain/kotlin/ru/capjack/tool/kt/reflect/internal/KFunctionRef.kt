package ru.capjack.tool.kt.reflect.internal

import kotlin.reflect.KFunction

internal open class KFunctionRef<out R>(
	name: String,
	returnType: KTypeRef,
	accessName: String,
	valueParameters: List<KParameterRef>,
	annotations: List<Annotation>
) : KCallableRef<R>(name, returnType, accessName, valueParameters, annotations), KFunction<R> {
	
	override val isInline: Boolean
		get() = throwNotImplemented()
	
	override val isExternal: Boolean
		get() = throwNotImplemented()
	
	override val isOperator: Boolean
		get() = throwNotImplemented()
	
	override val isInfix: Boolean
		get() = throwNotImplemented()
	
	override val isSuspend: Boolean
		get() = throwNotImplemented()
	
	override fun doCall(args: Array<*>): R {
		return args[0].asDynamic()[accessName].apply(args[0], args.drop(1).toTypedArray()).unsafeCast<R>()
	}
}
