package ru.capjack.tool.reflect.internal

import ru.capjack.tool.reflect.ReflectionException
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KType

internal val <T : Any> KClass<T>.ref: KClassRef<T>
	get() = this as? KClassRef<T> ?: (js.asDynamic().`$reflection$`?.unsafeCast<KClassRef<T>>() ?: throw ReflectionException(this))

internal val <T> KFunction<T>.ref: KFunctionRef<T>
	get() = this as? KFunctionRef<T> ?: throw ReflectionException(this)

internal val <T> KCallable<T>.ref: KCallableRef<T>
	get() = this as? KCallableRef<T> ?: throw ReflectionException(this)

internal val KParameter.ref: KParameterRef
	get() = this as? KParameterRef ?: throw ReflectionException(this)

internal val KType.ref: KTypeRef
	get() = this as? KTypeRef ?: throw ReflectionException(this)

internal val KAnnotatedElement.ref: KAnnotatedElementRef
	get() = this as? KAnnotatedElementRef
		?: (this as? KClass<*>)?.ref
		?: throw ReflectionException(this)


internal val JsClass<*>.metadata: JsMetadata
	get() = asDynamic().`$metadata$`.unsafeCast<JsMetadata>()

internal inline val KClass<*>.metadata: JsMetadata
	get() = js.metadata