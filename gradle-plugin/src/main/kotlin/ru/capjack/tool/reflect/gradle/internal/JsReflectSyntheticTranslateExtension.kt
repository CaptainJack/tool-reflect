package ru.capjack.tool.reflect.gradle.internal

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.js.translate.context.TranslationContext
import org.jetbrains.kotlin.js.translate.declaration.DeclarationBodyVisitor
import org.jetbrains.kotlin.js.translate.extensions.JsSyntheticTranslateExtension
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtPureClassOrObject
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.utils.ifEmpty
import ru.capjack.tool.reflect.gradle.JsReflectTarget

internal class JsReflectSyntheticTranslateExtension(private val configuration: JsReflectConfiguration) : JsSyntheticTranslateExtension {
	
	override fun generateClassSyntheticParts(declaration: KtPureClassOrObject, descriptor: ClassDescriptor, translator: DeclarationBodyVisitor, context: TranslationContext) {
		val units = defineReflectUnits(descriptor)
		
		if (units.isEmpty()) {
			return
		}
		
		JsReflectCodeGenerator(units, descriptor, context)
	}
	
	private fun defineReflectUnits(descriptor: ClassDescriptor): Set<JsReflectTarget.Unit> {
		return configuration.targets
			.filter { matchReflectTarget(it, descriptor) }
			.flatMap { it.units.ifEmpty { JsReflectTarget.Unit.values().toList() } }
			.toSet()
	}
	
	private fun matchReflectTarget(target: JsReflectTarget, descriptor: ClassDescriptor): Boolean {
		return when (target.type) {
			JsReflectTarget.Type.CLASS      -> matchReflectTargetName(descriptor.fqNameSafe, target.name)
			JsReflectTarget.Type.ANNOTATION -> descriptor.annotations.any { matchReflectTargetName(it.fqName!!, target.name) }
		}
	}
	
	private fun matchReflectTargetName(name: FqName, template: String): Boolean {
		val s = name.asString()
		return s == template
			|| (s.startsWith(template) && s[template.length] == '.')
			|| (template.last() == '*' && s.startsWith(template.dropLast(1)))
	}
}