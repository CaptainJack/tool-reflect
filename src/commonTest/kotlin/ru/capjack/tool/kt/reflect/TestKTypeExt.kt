package ru.capjack.tool.kt.reflect

import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("FunctionName")
class TestKTypeExt {
	
	@Test
	fun valid_kClass_Int_from_method_parameter() {
		val method = StubClassA::class.publicDeclaredMembers.first { it.name == "methodA1" }
		val parameter = method.valueParameters.first()
		val kClass = parameter.typeRef.kClass
		
		assertEquals(Int::class, kClass)
	}
	
	@Test
	fun valid_kClass_StubClassC_from_method_returnType() {
		val method = StubClassA::class.publicDeclaredMembers.first { it.name == "methodA2" }
		val kClass = method.returnTypeRef.kClass
		
		assertEquals(StubClassC::class, kClass)
	}
}