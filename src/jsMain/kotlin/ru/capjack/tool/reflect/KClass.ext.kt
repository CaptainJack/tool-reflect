package ru.capjack.tool.reflect

import ru.capjack.tool.reflect.internal.JsMetadataKind
import ru.capjack.tool.reflect.internal.metadata
import ru.capjack.tool.reflect.internal.ref
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

actual val KClass<*>.qualifiedNameRef: String?
	get() = ref.qualifiedName

actual val <T : Any> KClass<T>.primaryConstructor: KFunction<T>?
	get() = ref.primaryConstructor

actual val KClass<*>.isInterface: Boolean
	get() = metadata.kind == JsMetadataKind.INTERFACE

actual val KClass<*>.publicDeclaredMembers: Collection<KCallable<*>>
	get() = ref.publicDeclaredMembers

actual fun KClass<*>.getSupertypesWithAnnotation(clazz: KClass<out Annotation>): List<KClass<*>> {
	return ref.supertypes.asSequence().filter { it.hasAnnotation(clazz) }.map { it.kClass }.toList()
}

actual fun KClass<*>.isSubclassOf(base: KClass<*>): Boolean {
	return if (this == base) true
	else ref.supertypes.any { it.kClass.isSubclassOf(base) }
}
