package ru.capjack.tool.reflect

import kotlin.test.Test

@Suppress("FunctionName")
class TestKParameterExt {
	
	@Test
	fun valid_typeRef_from_StubClassA_methodA1_valueParameters() {
		val method = StubClassA::class.publicDeclaredMembers.first { it.name == "methodA1" }
		val parameter = method.valueParameters.first()
		parameter.typeRef
	}
}