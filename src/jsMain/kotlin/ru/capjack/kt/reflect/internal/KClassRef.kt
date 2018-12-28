package ru.capjack.kt.reflect.internal

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KTypeParameter
import kotlin.reflect.KVisibility

internal class KClassRef<T : Any>(
	private val kClass: KClass<T>,
	private val pkg: String?,
	
	override val supertypes: List<KTypeRef>,
	
	val primaryConstructor: JsConstructor<T>?,
	val publicDeclaredMembers: Collection<KCallableRef<*>>,
	
	annotations: List<Annotation>

) : KAnnotatedElementRef(annotations), KClass<T> {
	
	override val simpleName: String?
		get() = kClass.simpleName
	
	override val qualifiedName: String?
		get() = when (null) {
			simpleName, pkg -> null
			else            -> "$pkg.$simpleName"
		}
	
	override val constructors: Collection<KFunction<T>>
		get() = throwNotImplemented()
	
	override val isAbstract: Boolean
		get() = throwNotImplemented()
	
	override val isCompanion: Boolean
		get() = throwNotImplemented()
	
	override val isData: Boolean
		get() = throwNotImplemented()
	
	override val isFinal: Boolean
		get() = throwNotImplemented()
	
	override val isInner: Boolean
		get() = throwNotImplemented()
	
	override val isOpen: Boolean
		get() = throwNotImplemented()
	
	override val isSealed: Boolean
		get() = throwNotImplemented()
	
	override val members: Collection<KCallable<*>>
		get() = throwNotImplemented()
	
	override val nestedClasses: Collection<KClass<*>>
		get() = throwNotImplemented()
	
	override val objectInstance: T?
		get() = throwNotImplemented()
	
	override val typeParameters: List<KTypeParameter>
		get() = throwNotImplemented()
	
	override val visibility: KVisibility?
		get() = throwNotImplemented()
	
	override val sealedSubclasses: List<KClass<out T>>
		get() = throwNotImplemented()
	
	override fun isInstance(value: Any?): Boolean {
		return kClass.isInstance((value as? KClassRef<*>)?.kClass ?: value)
	}
	
	override fun equals(other: Any?): Boolean {
		return this === other || kClass == (other as? KClassRef<*>)?.kClass ?: other
	}
	
	override fun hashCode(): Int {
		return kClass.hashCode()
	}
	
	override fun toString(): String {
		return when (metadata.kind) {
			JsMetadataKind.CLASS     -> "class"
			JsMetadataKind.INTERFACE -> "interface"
			JsMetadataKind.OBJECT    -> "object"
		} + " $qualifiedName"
	}
}
