package ru.capjack.kt.reflect.js.gradle.internal

import org.jetbrains.kotlin.builtins.KotlinBuiltIns.isAnyOrNullableAny
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.ir.backend.js.utils.getClassifier
import org.jetbrains.kotlin.js.backend.ast.*
import org.jetbrains.kotlin.js.translate.context.Namer
import org.jetbrains.kotlin.js.translate.context.TranslationContext
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils
import org.jetbrains.kotlin.js.translate.utils.getReferenceToJsClass
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.addParameter
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.addStatement
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.resolve.annotations.argumentValue
import org.jetbrains.kotlin.resolve.constants.*
import org.jetbrains.kotlin.resolve.descriptorUtil.annotationClass
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.types.KotlinType
import ru.capjack.kt.reflect.js.gradle.ReflectTarget

class ReflectCodeGenerator(
	units: Set<ReflectTarget.Unit>,
	descriptor: ClassDescriptor,
	private val context: TranslationContext
) {
	
	private val factory = getReference(context.currentModule.getClassifier(FqName("ru.capjack.kt.reflect.Reflections"))!!)
	
	init {
		context.addTopLevelStatement(
			callFactory(
				"reflectClass",
				getReference(descriptor),
				JsStringLiteral(descriptor.fqNameSafe.parent().asString()),
				if (units.contains(ReflectTarget.Unit.SUPERTYPES)) extractSupertypes(descriptor) else JsArrayLiteral(),
				if (units.contains(ReflectTarget.Unit.CONSTRUCTOR)) makeJsConstructor(descriptor) else JsNullLiteral(),
				if (units.contains(ReflectTarget.Unit.MEMBERS)) extractMembers(descriptor) else JsArrayLiteral(),
				if (units.contains(ReflectTarget.Unit.ANNOTATIONS)) extractAnnotations(descriptor) else JsArrayLiteral()
			).makeStmt()
		)
		
	}
	
	private fun getReference(descriptor: DeclarationDescriptor): JsExpression {
		return context.getInnerReference(descriptor)
	}
	
	private fun extractSupertypes(descriptor: ClassDescriptor): JsExpression {
		return JsArrayLiteral(
			descriptor.defaultType.constructor.supertypes
				.filterNot(::isAnyOrNullableAny)
				.map(::makeKTypeRef)
				.toList()
		)
	}
	
	private fun makeJsConstructor(descriptor: ClassDescriptor): JsExpression {
		val parameters = descriptor.unsubstitutedPrimaryConstructor?.valueParameters
			?: return JsNullLiteral()
		
		return callFactory(
			"createJsConstructor",
			getReference(descriptor),
			extractParameters(parameters),
			context.createRootScopedFunction("create").apply {
				val a = addParameter("a")
				addStatement(JsReturn(JsNew(getReference(descriptor), (0 until parameters.size).map { JsArrayAccess(JsNameRef(a.name), JsIntLiteral(it)) })))
			}
		)
	}
	
	private fun extractParameters(parameters: List<ValueParameterDescriptor>): JsExpression {
		return JsArrayLiteral(parameters.map(::makeKParameterRef))
	}
	
	private fun makeKParameterRef(descriptor: ValueParameterDescriptor): JsExpression {
		return callFactory(
			"createKParameterRef",
			JsIntLiteral(descriptor.index),
			JsStringLiteral(descriptor.name.asString()),
			makeKTypeRef(descriptor.type),
			extractAnnotations(descriptor)
		)
	}
	
	private fun extractMembers(descriptor: ClassDescriptor): JsExpression {
		val members = descriptor.unsubstitutedMemberScope.getContributedDescriptors { true }
			.mapNotNull { it as? CallableMemberDescriptor }
			.filter { it.kind.isReal && it.visibility.normalize().name == "public" }
			.mapNotNull {
				when (it) {
					is PropertyDescriptor -> makeKPropertyRef(it)
					is FunctionDescriptor -> makeKFunctionRef(it)
					else                  -> null
				}
			}
			
			.toList()
		
		return JsArrayLiteral(members)
	}
	
	private fun makeKPropertyRef(descriptor: PropertyDescriptor): JsExpression {
		return callFactory(
			"createKPropertyRef",
			JsStringLiteral(descriptor.name.asString()),
			makeKTypeRef(descriptor.returnType!!),
			JsStringLiteral(context.getNameForDescriptor(descriptor).ident),
			extractParameters(descriptor.valueParameters),
			extractAnnotations(descriptor)
		)
	}
	
	private fun makeKFunctionRef(descriptor: FunctionDescriptor): JsExpression {
		return callFactory(
			"createKFunctionRef",
			JsStringLiteral(descriptor.name.asString()),
			makeKTypeRef(descriptor.returnType!!),
			JsStringLiteral(context.getNameForDescriptor(descriptor).ident),
			extractParameters(descriptor.valueParameters),
			extractAnnotations(descriptor)
		)
	}
	
	private fun makeKTypeRef(type: KotlinType): JsExpression {
		return callFactory(
			"createKTypeRef",
			getReferenceToJsClass(type, context),
			extractAnnotations(type.annotations)
		)
	}
	
	private fun extractAnnotations(descriptor: DeclarationDescriptor): JsExpression {
		return extractAnnotations(descriptor.annotations)
	}
	
	private fun extractAnnotations(annotations: Annotations): JsExpression {
		return JsArrayLiteral(annotations
			.filter { annotation ->
				annotation.annotationClass!!.annotations.findAnnotation(FqName("kotlin.annotation.Retention"))
					?.let { it.allValueArguments[Name.identifier("value")]?.accept(RETENTION_ARGUMENT_VISITOR, Unit) == AnnotationRetention.RUNTIME }
					?: true
			}
			.map { annotation ->
				
				
				val clazz = annotation.annotationClass!!
				val parameters = clazz.unsubstitutedPrimaryConstructor!!.valueParameters
				
				JsNew(
					context.getInnerReference(clazz),
					parameters.map { d ->
						annotation.argumentValue(d.name.asString())
							?.accept(ANNOTATION_ARGUMENT_VISITOR, context)
							?: Namer.getUndefinedExpression()
					}
				)
			}
		)
	}
	
	private fun callFactory(method: String, vararg args: JsExpression): JsExpression {
		return JsAstUtils.invokeMethod(factory, factoryMethodsNames.getValue(method), *args)
	}
	
	companion object {
		val factoryMethodsNames = mapOf(
			"reflectClass" to "a",
			"createJsConstructor" to "b",
			"createKTypeRef" to "c",
			"createKParameterRef" to "d",
			"createKFunctionRef" to "e",
			"createKPropertyRef" to "f"
		)
		
		val RETENTION_ARGUMENT_VISITOR = object : EmptyAnnotationArgumentVisitor<AnnotationRetention, Unit>() {
			override fun visitEnumValue(value: EnumValue, data: Unit): AnnotationRetention {
				return AnnotationRetention.valueOf(value.enumEntryName.asString())
			}
		}
		
		val ANNOTATION_ARGUMENT_VISITOR = object : EmptyAnnotationArgumentVisitor<JsExpression, TranslationContext>() {
			
			override fun visitNullValue(value: NullValue, data: TranslationContext) =
				JsNullLiteral()
			
			override fun visitBooleanValue(value: BooleanValue, data: TranslationContext) =
				JsBooleanLiteral(value.value)
			
			override fun visitByteValue(value: ByteValue, data: TranslationContext) =
				JsIntLiteral(value.value.toInt())
			
			override fun visitShortValue(value: ShortValue, data: TranslationContext) =
				JsIntLiteral(value.value.toInt())
			
			override fun visitIntValue(value: IntValue, data: TranslationContext) =
				JsIntLiteral(value.value)
			
			override fun visitLongValue(value: LongValue, data: TranslationContext) =
				JsAstUtils.newLong(value.value)
			
			override fun visitFloatValue(value: FloatValue, data: TranslationContext) =
				JsDoubleLiteral(value.value.toDouble())
			
			override fun visitDoubleValue(value: DoubleValue, data: TranslationContext) =
				JsDoubleLiteral(value.value)
			
			override fun visitCharValue(value: CharValue, data: TranslationContext) =
				JsIntLiteral(value.value.toInt())
			
			override fun visitStringValue(value: StringValue, data: TranslationContext) =
				JsStringLiteral(value.value)
			
			override fun visitUByteValue(value: UByteValue, data: TranslationContext) =
				JsIntLiteral(value.value.toInt())
			
			override fun visitUShortValue(value: UShortValue, data: TranslationContext) =
				JsIntLiteral(value.value.toInt())
			
			override fun visitUIntValue(value: UIntValue, data: TranslationContext) =
				JsIntLiteral(value.value)
			
			override fun visitULongValue(value: ULongValue, data: TranslationContext) =
				JsAstUtils.newLong(value.value)
			
			override fun visitArrayValue(value: ArrayValue, data: TranslationContext) =
				JsArrayLiteral(value.value.map { it.accept(this, data) })
			
			override fun visitKClassValue(value: KClassValue, data: TranslationContext) =
				JsInvocation(data.getReferenceToIntrinsic(Namer.GET_KCLASS), getReferenceToJsClass(value.value, data))
		}
	}
}
