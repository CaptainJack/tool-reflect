package ru.capjack.tool.reflect

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Suppress("FunctionName")
class TestKCallableExt {
	@Test
	fun valid_valueParameters_from_StubClassA_propertyA1() {
		val property = StubClassA::class.publicDeclaredMembers.first { it.name == "propertyA1" }
		assertTrue(property.valueParameters.isEmpty())
	}
	
	@Test
	fun valid_returnTypeRef_from_StubClassA_propertyA1() {
		val property = StubClassA::class.publicDeclaredMembers.first { it.name == "propertyA1" }
		property.returnTypeRef
	}
	
	@Test
	fun valid_callRef_on_StubClassA_propertyA1() {
		val obj = StubClassA("a")
		val property = StubClassA::class.publicDeclaredMembers.first { it.name == "propertyA1" }
		assertEquals("a", property.callRef(obj))
	}
	
	@Test
	fun valid_valueParameters_from_StubClassA_methodA1() {
		val method = StubClassA::class.publicDeclaredMembers.first { it.name == "methodA1" }
		assertEquals(1, method.valueParameters.size)
	}
	
	@Test
	fun valid_returnTypeRef_from_StubClassA_methodA1() {
		val method = StubClassA::class.publicDeclaredMembers.first { it.name == "methodA1" }
		method.returnTypeRef
	}
	
	@Test
	fun valid_callRef_on_StubClassA_methodA1() {
		val obj = StubClassA("a")
		val method = StubClassA::class.publicDeclaredMembers.first { it.name == "methodA1" }
		assertEquals("1", method.callRef(obj, 1))
	}
}