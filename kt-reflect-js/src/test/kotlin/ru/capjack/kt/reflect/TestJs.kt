package ru.capjack.kt.reflect

import ru.capjack.kt.reflect.internal.ref
import kotlin.reflect.KAnnotatedElement
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

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
	
	@Test
	fun present_NameMask() {
		StubReflectNameMask::class.ref
	}
	
	@Test
	fun present_A() {
		val ref = StubReflectA::class.ref
		
		assertTrue(ref.annotations.isNotEmpty())
		assertNull(ref.primaryConstructor)
		assertTrue(ref.supertypes.isEmpty())
		assertTrue(ref.publicDeclaredMembers.isEmpty())
	}
	
	@Test
	fun present_B() {
		val ref = StubReflectB::class.ref
		
		assertTrue(ref.annotations.isEmpty())
		assertNull(ref.primaryConstructor)
		assertTrue(ref.supertypes.isNotEmpty())
		assertTrue(ref.publicDeclaredMembers.isEmpty())
	}
	
	@Test
	fun present_C() {
		val ref = StubReflectC::class.ref
		
		assertTrue(ref.annotations.isEmpty())
		assertNotNull(ref.primaryConstructor)
		assertTrue(ref.supertypes.isEmpty())
		assertTrue(ref.publicDeclaredMembers.isEmpty())
	}
	
	
	@Test
	fun present_D() {
		val ref = StubReflectD::class.ref
		
		assertTrue(ref.annotations.isEmpty())
		assertNull(ref.primaryConstructor)
		assertTrue(ref.supertypes.isEmpty())
		assertTrue(ref.publicDeclaredMembers.isNotEmpty())
	}
}