package ru.capjack.kt.reflect

import ru.capjack.kt.reflect.internal.JsConstructor
import ru.capjack.kt.reflect.internal.KCallableRef
import ru.capjack.kt.reflect.internal.KClassRef
import ru.capjack.kt.reflect.internal.KFunctionRef
import ru.capjack.kt.reflect.internal.KParameterRef
import ru.capjack.kt.reflect.internal.KPropertyRef
import ru.capjack.kt.reflect.internal.KTypeRef

@Suppress("unused", "MemberVisibilityCanBePrivate")
object Reflections {
	private val classes = mutableListOf<JsClass<*>>()
	
	fun erase() {
		classes
			.onEach { it.asDynamic().`$reflection$` = null }
			.clear()
	}
	
	@JsName("a")
	internal fun <T : Any> reflectClass(
		jsClass: JsClass<T>,
		pkg: String?,
		supertypes: Array<KTypeRef>,
		constructor: JsConstructor<T>?,
		members: Array<KCallableRef<*>>,
		annotations: Array<Annotation>
	) {
		classes.add(jsClass)
		jsClass.asDynamic().`$reflection$` = KClassRef(
			jsClass.kotlin,
			pkg,
			supertypes.toList(),
			constructor,
			members.toList(),
			annotations.toList()
		)
	}
	
	@JsName("b")
	internal fun <T : Any> createJsConstructor(jsClass: JsClass<T>, valueParameters: Array<KParameterRef>, creator: dynamic): JsConstructor<T> {
		return JsConstructor("<init>", createKTypeRef(jsClass, emptyArray()), valueParameters.toList(), emptyList(), creator)
	}
	
	@JsName("c")
	internal fun createKTypeRef(jsClass: JsClass<*>, annotations: Array<Annotation>): KTypeRef {
		return KTypeRef(jsClass.kotlin, annotations.toList())
	}
	
	@JsName("d")
	internal fun createKParameterRef(index: Int, name: String, type: KTypeRef, annotations: Array<Annotation>): KParameterRef {
		return KParameterRef(index + 1, name, type, annotations.toList())
	}
	
	@JsName("e")
	internal fun <R> createKFunctionRef(
		name: String,
		returnType: KTypeRef,
		accessName: String,
		valueParameters: Array<KParameterRef>,
		annotations: Array<Annotation>
	): KCallableRef<R> {
		return KFunctionRef(name, returnType, accessName, valueParameters.toList(), annotations.toList())
	}
	
	@JsName("f")
	internal fun <R> createKPropertyRef(
		name: String,
		returnType: KTypeRef,
		accessName: String,
		valueParameters: Array<KParameterRef>,
		annotations: Array<Annotation>
	): KCallableRef<R> {
		return KPropertyRef(name, returnType, accessName, valueParameters.toList(), annotations.toList())
	}
}
