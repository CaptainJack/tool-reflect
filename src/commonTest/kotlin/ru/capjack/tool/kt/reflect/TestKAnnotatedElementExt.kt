package ru.capjack.tool.kt.reflect

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@Suppress("FunctionName")
class TestKAnnotatedElementExt {
	
	@Test
	fun findAnnotation_is_null() {
		assertNull(StubClassA::class.findAnnotation(StubAnnotationA::class))
	}
	
	@Test
	fun findAnnotation_not_null_and_valid() {
		val annotation = StubClassB::class.findAnnotation(StubAnnotationA::class)
		assertNotNull(annotation)
		assertEquals("bbb", annotation.param)
	}
}