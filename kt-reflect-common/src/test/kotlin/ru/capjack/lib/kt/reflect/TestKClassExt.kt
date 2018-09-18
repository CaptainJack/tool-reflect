package ru.capjack.lib.kt.reflect

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@Suppress("FunctionName")
class TestKClassExt {
	@Test
	fun qualifiedNameRef() {
		assertEquals("ru.capjack.lib.kt.reflect.StubClassA", StubClassA::class.qualifiedNameRef)
	}
	
	@Test
	fun primaryConstructor_is_exists_on_class() {
		assertNotNull(StubClassA::class.primaryConstructor)
	}
	
	@Test
	fun primaryConstructor_not_exists_on_interface() {
		assertNull(StubInterfaceA::class.primaryConstructor)
	}
	
	@Test
	fun isInterface_false_on_class() {
		assertFalse(StubClassA::class.isInterface)
	}
	
	@Test
	fun isInterface_true_on_interface() {
		assertTrue(StubInterfaceA::class.isInterface)
	}
	
	@Test
	fun publicDeclaredMembers_is_empty() {
		assertTrue(StubClassB::class.publicDeclaredMembers.isEmpty())
	}
	
	@Test
	fun publicDeclaredMembers_not_empty() {
		assertTrue(StubClassA::class.publicDeclaredMembers.isNotEmpty())
	}
	
	@Test
	fun getSupertypesWithAnnotation_is_empty() {
		assertTrue(StubClassA::class.getSupertypesWithAnnotation<StubAnnotationB>().isEmpty())
	}
	
	@Test
	fun getSupertypesWithAnnotation_not_empty() {
		assertTrue(StubClassB::class.getSupertypesWithAnnotation<StubAnnotationB>().isNotEmpty())
	}
	
	@Test
	fun publicDeclaredMembers_of_StubClassA_contains_propertyA1() {
		assertTrue(StubClassA::class.publicDeclaredMembers.any { it.name == "propertyA1" })
	}
	
	@Test
	fun publicDeclaredMembers_of_StubClassA_contains_methodA1() {
		assertTrue(StubClassA::class.publicDeclaredMembers.any { it.name == "methodA1" })
	}
	
	@Test
	fun publicDeclaredMembers_of_StubClassA_contains_methodA2() {
		assertTrue(StubClassA::class.publicDeclaredMembers.any { it.name == "methodA2" })
	}
}