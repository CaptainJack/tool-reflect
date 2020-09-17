package ru.capjack.tool.reflect

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KType


expect val KCallable<*>.valueParameters: List<KParameter>

expect val KCallable<*>.returnTypeRef: KType

expect fun <T> KCallable<T>.callRef(vararg args: Any?): T

expect fun <T : Annotation> KCallable<*>.findAnnotation(clazz: KClass<T>): T?


expect val KClass<*>.qualifiedNameRef: String?

expect val <T : Any> KClass<T>.primaryConstructor: KFunction<T>?

expect val KClass<*>.isInterface: Boolean

expect val KClass<*>.publicDeclaredMembers: Collection<KCallable<*>>

expect fun KClass<*>.getSupertypesWithAnnotation(clazz: KClass<out Annotation>): List<KClass<*>>

expect fun KClass<*>.isSubclassOf(base: KClass<*>): Boolean

expect fun <T : Annotation> KClass<*>.findAnnotation(clazz: KClass<T>): T?


expect interface KParameter {
	val index: Int
	val name: String?
	val type: KType
}

expect fun <T : Annotation> KParameter.findAnnotation(clazz: KClass<T>): T?


expect val KType.kClass: KClass<*>