@file:Suppress("NOTHING_TO_INLINE")

package ru.capjack.tool.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated
import kotlin.reflect.jvm.internal.impl.name.FqName
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.KParameter as KParameterSource

actual inline val KCallable<*>.valueParameters: List<KParameter> get() = valueParameters

actual inline val KCallable<*>.returnTypeRef: KType get() = returnType

actual inline fun <T> KCallable<T>.callRef(vararg args: Any?) = call(*args)

actual fun <T : Annotation> KCallable<*>.findAnnotation(clazz: KClass<T>) = findAnnotation0(clazz)


actual inline val KClass<*>.qualifiedNameRef: String? get() = qualifiedName

actual inline val <T : Any> KClass<T>.primaryConstructor: KFunction<T>? get() = primaryConstructor

actual inline val KClass<*>.isInterface: Boolean get() = java.isInterface

actual inline val KClass<*>.publicDeclaredMembers: Collection<KCallable<*>> get() = declaredMembers.filter { it.visibility == KVisibility.PUBLIC }

actual fun KClass<*>.getSupertypesWithAnnotation(clazz: KClass<out Annotation>): List<KClass<*>> {
	val name = FqName(clazz.qualifiedName!!)
	return supertypes
		.asSequence()
		.filter { t ->
			//WTF: JetBrains, why KType not implements KAnnotatedElement?
			(t::class.java.getDeclaredMethod("getType").invoke(t) as? Annotated)?.annotations?.hasAnnotation(name) ?: false
		}
		.map { it.kClass }
		.toList()
}

actual inline fun KClass<*>.isSubclassOf(base: KClass<*>): Boolean = isSubclassOf(base)

actual fun <T : Annotation> KClass<*>.findAnnotation(clazz: KClass<T>) = findAnnotation0(clazz)


actual typealias KParameter = KParameterSource

actual fun <T : Annotation> KParameter.findAnnotation(clazz: KClass<T>) = findAnnotation0(clazz)


actual inline val KType.kClass: KClass<*> get() = jvmErasure