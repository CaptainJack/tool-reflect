package ru.capjack.lib.kt.reflect

import ru.capjack.lib.kt.reflect.internal.ref
import kotlin.reflect.KAnnotatedElement
import kotlin.test.Test

@Suppress("FunctionName")
class TestJs {
	@Test
	fun present_reflections() {
		StubClassA::class.ref
		StubClassB::class.ref
	}
	
	@Test
	fun present_KClass_as_KAnnotatedElement_and_it_has_reflection() {
		val kClass = StubClassA::class as KAnnotatedElement
		kClass.ref
	}
	
	@Test
	fun present_KCallable_accessName() {
		val property = StubClassA::class.publicDeclaredMembers.first { it.name == "propertyA1" }
		property.accessName
	}
}