package ru.capjack.lib.kt.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated
import kotlin.reflect.jvm.internal.impl.name.FqName

actual inline val KClass<*>.qualifiedNameRef: String?
	get() = qualifiedName

actual inline val <T : Any> KClass<T>.primaryConstructor: KFunction<T>?
	get() = primaryConstructor

actual inline val KClass<*>.isInterface: Boolean
	get() = java.isInterface

actual inline val KClass<*>.publicDeclaredMembers: Collection<KCallable<*>>
	get() = declaredMembers.filter { it.visibility == KVisibility.PUBLIC }

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