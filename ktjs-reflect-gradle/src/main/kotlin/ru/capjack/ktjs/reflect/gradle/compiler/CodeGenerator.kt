package ru.capjack.ktjs.reflect.gradle.compiler

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
import org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.js.backend.ast.*
import org.jetbrains.kotlin.js.translate.context.Namer
import org.jetbrains.kotlin.js.translate.context.TranslationContext
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils
import org.jetbrains.kotlin.js.translate.utils.getReferenceToJsClass
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.addParameter
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.addStatement
import org.jetbrains.kotlin.resolve.constants.*
import org.jetbrains.kotlin.resolve.descriptorUtil.annotationClass
import org.jetbrains.kotlin.types.typeUtil.isUnit

object CodeGenerator {
	val ANNOTATION_ARGUMENT_VISITOR = object :
		AnnotationArgumentVisitor<JsExpression, TranslationContext> {
		override fun visitNullValue(value: NullValue, data: TranslationContext) = JsNullLiteral()
		override fun visitBooleanValue(value: BooleanValue, data: TranslationContext) = JsBooleanLiteral(value.value)
		override fun visitByteValue(value: ByteValue, data: TranslationContext) = JsIntLiteral(value.value.toInt())
		override fun visitShortValue(value: ShortValue, data: TranslationContext) = JsIntLiteral(value.value.toInt())
		override fun visitIntValue(value: IntValue, data: TranslationContext) = JsIntLiteral(value.value)
		override fun visitLongValue(value: LongValue, data: TranslationContext) = JsAstUtils.newLong(value.value)
		override fun visitFloatValue(value: FloatValue, data: TranslationContext) = JsDoubleLiteral(value.value.toDouble())
		override fun visitDoubleValue(value: DoubleValue, data: TranslationContext) = JsDoubleLiteral(value.value)
		override fun visitCharValue(value: CharValue, data: TranslationContext) = JsIntLiteral(value.value.toInt())
		override fun visitStringValue(value: StringValue, data: TranslationContext) = JsStringLiteral(value.value)
		override fun visitUByteValue(value: UByteValue, data: TranslationContext) = JsIntLiteral(value.value.toInt())
		override fun visitUShortValue(value: UShortValue, data: TranslationContext) = JsIntLiteral(value.value.toInt())
		override fun visitUIntValue(value: UIntValue, data: TranslationContext) = JsIntLiteral(value.value)
		override fun visitULongValue(value: ULongValue, data: TranslationContext) = JsAstUtils.newLong(value.value)
		override fun visitArrayValue(value: ArrayValue, data: TranslationContext) = JsArrayLiteral(value.value.map { it.accept(this, data) })
		override fun visitEnumValue(value: EnumValue, data: TranslationContext) = visitUnsupportedValue(value)
		override fun visitAnnotationValue(value: AnnotationValue, data: TranslationContext) = visitUnsupportedValue(value)
		override fun visitKClassValue(value: KClassValue, data: TranslationContext) =
			JsInvocation(data.getReferenceToIntrinsic(Namer.GET_KCLASS), getReferenceToJsClass(value.value, data))
		
		override fun visitErrorValue(value: ErrorValue, data: TranslationContext) = visitUnsupportedValue(value)
		
		private fun visitUnsupportedValue(value: ConstantValue<*>): Nothing {
			throw UnsupportedOperationException("Unsupported annotation value $value")
		}
	}
	
	fun jsArray(vararg properties: JsExpression): JsArrayLiteral {
		return jsArray(properties.toList())
	}
	
	fun jsArray(properties: List<JsExpression>): JsArrayLiteral {
		return JsArrayLiteral(properties)
	}
	
	fun jsObject(vararg properties: JsPropertyInitializer): JsObjectLiteral {
		return jsObject(properties.toList())
	}
	
	fun jsObject(properties: List<JsPropertyInitializer>): JsObjectLiteral {
		return JsObjectLiteral(properties, true)
	}
	
	fun jsObject(init: (MutableCollection<JsPropertyInitializer>) -> Unit): JsObjectLiteral {
		return jsObject().apply { init(this.propertyInitializers) }
	}
	
	fun jsProperty(name: String, value: JsExpression): JsPropertyInitializer {
		return JsPropertyInitializer(JsNameRef(name), value)
	}
	
	fun generateConstructor(descriptor: ClassDescriptor, context: TranslationContext): JsExpression {
		val parameters = descriptor.unsubstitutedPrimaryConstructor!!.valueParameters
		return jsArray(
			generateParameters(parameters, context),
			context.createRootScopedFunction("create").apply {
				val pArgs = addParameter("a")
				addStatement(
					JsReturn(
						JsNew(
							context.getInnerReference(descriptor),
							(0 until parameters.size).map {
								JsArrayAccess(JsNameRef(pArgs.name), JsIntLiteral(it))
							}
						)
					)
				)
			}
		)
	}
	
	fun generateMethods(methods: List<FunctionDescriptor>, context: TranslationContext): JsExpression {
		return jsArray(methods.map { m ->
			jsArray(
				mutableListOf(
					JsStringLiteral(context.getNameForDescriptor(m).ident),
					JsStringLiteral(m.name.asString()),
					generateParameters(m.valueParameters, context),
					m.returnType!!.let {
						if (it.isUnit()) JsNullLiteral()
						else getReferenceToJsClass(it, context)
					}
				).apply {
					if (!m.annotations.isEmpty()) {
						add(generateAnnotations(m.annotations, context))
					}
				}
			)
		})
	}
	
	fun generateParameters(parameters: List<ValueParameterDescriptor>, context: TranslationContext): JsExpression {
		return jsArray(parameters.map { p ->
			jsArray(
				mutableListOf(
					JsStringLiteral(p.name.asString()),
					getReferenceToJsClass(p.type, context)
				).apply {
					if (!p.annotations.isEmpty()) {
						add(generateAnnotations(p.annotations, context))
					}
				}
			)
		})
	}
	
	fun generateAnnotations(annotations: Annotations, context: TranslationContext): JsExpression {
		return JsArrayLiteral(annotations.map { annotation ->
			val arguments = annotation.allValueArguments
			if (arguments.isEmpty()) {
				context.getInnerReference(annotation.annotationClass!!)
			}
			else {
				JsNew(
					context.getInnerReference(annotation.annotationClass!!),
					arguments.map { it.value.accept(ANNOTATION_ARGUMENT_VISITOR, context) }
				)
			}
		})
	}
}