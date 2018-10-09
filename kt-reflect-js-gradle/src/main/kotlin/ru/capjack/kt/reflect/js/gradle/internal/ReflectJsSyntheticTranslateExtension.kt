package ru.capjack.kt.reflect.js.gradle.internal

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.js.translate.context.TranslationContext
import org.jetbrains.kotlin.js.translate.declaration.DeclarationBodyVisitor
import org.jetbrains.kotlin.js.translate.extensions.JsSyntheticTranslateExtension
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtPureClassOrObject
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.utils.ifEmpty
import ru.capjack.kt.reflect.js.gradle.ReflectTarget

internal class ReflectJsSyntheticTranslateExtension(private val configuration: ReflectConfiguration) : JsSyntheticTranslateExtension {
	
	override fun generateClassSyntheticParts(declaration: KtPureClassOrObject, descriptor: ClassDescriptor, translator: DeclarationBodyVisitor, context: TranslationContext) {
		val units = defineReflectUnits(descriptor)
		
		if (units.isEmpty()) {
			return
		}
		
		ReflectCodeGenerator(units, descriptor, context)
	}
	
	private fun defineReflectUnits(descriptor: ClassDescriptor): Set<ReflectTarget.Unit> {
		return configuration.targets
			.filter { matchReflectTarget(it, descriptor) }
			.flatMap { it.units.ifEmpty { ReflectTarget.Unit.values().toList() } }
			.toSet()
	}
	
	private fun matchReflectTarget(target: ReflectTarget, descriptor: ClassDescriptor): Boolean {
		return when (target.type) {
			ReflectTarget.Type.CLASS      -> matchReflectTargetName(descriptor.fqNameSafe, target.name)
			ReflectTarget.Type.ANNOTATION -> descriptor.annotations.any { matchReflectTargetName(it.fqName!!, target.name) }
		}
	}
	
	private fun matchReflectTargetName(name: FqName, template: String): Boolean {
		val s = name.asString()
		return s == template
			|| (s.startsWith(template) && s[template.length] == '.')
			|| (template.last() == '*' && s.startsWith(template.dropLast(1)))
	}
}