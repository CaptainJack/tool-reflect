package ru.capjack.tool.kt.reflect.gradle.internal

import org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
import org.jetbrains.kotlin.resolve.constants.*

open class EmptyAnnotationArgumentVisitor<R, D> : AnnotationArgumentVisitor<R, D> {
	override fun visitBooleanValue(value: BooleanValue, data: D): R = visitUnsupportedValue(value)
	override fun visitShortValue(value: ShortValue, data: D): R = visitUnsupportedValue(value)
	override fun visitByteValue(value: ByteValue, data: D): R = visitUnsupportedValue(value)
	override fun visitNullValue(value: NullValue, data: D): R = visitUnsupportedValue(value)
	override fun visitDoubleValue(value: DoubleValue, data: D): R = visitUnsupportedValue(value)
	override fun visitAnnotationValue(value: AnnotationValue, data: D): R = visitUnsupportedValue(value)
	override fun visitLongValue(value: LongValue, data: D): R = visitUnsupportedValue(value)
	override fun visitCharValue(value: CharValue, data: D): R = visitUnsupportedValue(value)
	override fun visitUIntValue(value: UIntValue, data: D): R = visitUnsupportedValue(value)
	override fun visitUShortValue(value: UShortValue, data: D): R = visitUnsupportedValue(value)
	override fun visitIntValue(value: IntValue, data: D): R = visitUnsupportedValue(value)
	override fun visitKClassValue(value: KClassValue, data: D): R = visitUnsupportedValue(value)
	override fun visitErrorValue(value: ErrorValue, data: D): R = visitUnsupportedValue(value)
	override fun visitFloatValue(value: FloatValue, data: D): R = visitUnsupportedValue(value)
	override fun visitEnumValue(value: EnumValue, data: D): R = visitUnsupportedValue(value)
	override fun visitUByteValue(value: UByteValue, data: D): R = visitUnsupportedValue(value)
	override fun visitULongValue(value: ULongValue, data: D): R = visitUnsupportedValue(value)
	override fun visitArrayValue(value: ArrayValue, data: D): R = visitUnsupportedValue(value)
	override fun visitStringValue(value: StringValue, data: D): R = visitUnsupportedValue(value)
	
	private fun visitUnsupportedValue(value: ConstantValue<*>): Nothing {
		throw UnsupportedOperationException("Unsupported annotation value $value")
	}
}
