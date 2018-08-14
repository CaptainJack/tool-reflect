package ru.capjack.ktjs.reflect.gradle.compiler

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.js.backend.ast.JsNameRef
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer
import org.jetbrains.kotlin.js.translate.context.Namer
import org.jetbrains.kotlin.js.translate.context.TranslationContext
import org.jetbrains.kotlin.js.translate.declaration.DeclarationBodyVisitor
import org.jetbrains.kotlin.js.translate.extensions.JsSyntheticTranslateExtension
import org.jetbrains.kotlin.js.translate.utils.BindingUtils
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtPureClassOrObject
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import ru.capjack.ktjs.reflect.gradle.compiler.CodeGenerator.generateAnnotations
import ru.capjack.ktjs.reflect.gradle.compiler.CodeGenerator.generateConstructor
import ru.capjack.ktjs.reflect.gradle.compiler.CodeGenerator.generateMethods
import ru.capjack.ktjs.reflect.gradle.compiler.CodeGenerator.jsObject
import ru.capjack.ktjs.reflect.gradle.compiler.CodeGenerator.jsProperty

class JsSyntheticTranslateExtension(
	private val configurations: List<ReflectConfiguration>
) : JsSyntheticTranslateExtension {
	
	override fun generateClassSyntheticParts(declaration: KtPureClassOrObject, descriptor: ClassDescriptor, translator: DeclarationBodyVisitor, context: TranslationContext) {
		var reflect = false
		val parts = mutableSetOf<ReflectKind>()
		for (configuration in configurations) {
			reflect = collectParts(parts, configuration, descriptor) || reflect
		}
		
		if (reflect) {
			val metadata = mutableListOf<JsPropertyInitializer>()
			
			descriptor.annotations.also {
				if (!it.isEmpty()) {
					metadata.add(jsProperty("annotations", generateAnnotations(descriptor.annotations, context)))
				}
			}
			
			parts.forEach { p ->
				when (p) {
					ReflectKind.CONSTRUCTOR ->
						metadata.add(jsProperty("constructor", generateConstructor(descriptor, context)))
					ReflectKind.METHODS     ->
						metadata.add(
							jsProperty(
								"methods",
								generateMethods(
									declaration.declarations
										.filterIsInstance<KtNamedFunction>()
										.map {
											val d = BindingUtils.getFunctionDescriptor(context.bindingContext(), it)
											d
										},
									context
								)
							)
						)
				}
			}
			
			
			context.addTopLevelStatement(
				JsAstUtils.assignment(
					JsNameRef("reflect", JsNameRef(Namer.METADATA, context.getInnerReference(descriptor))),
					jsObject(metadata)
				).makeStmt()
			)
		}
	}
	
	private fun collectParts(kinds: MutableCollection<ReflectKind>, configuration: ReflectConfiguration, descriptor: ClassDescriptor): Boolean {
		return if (matchConfiguration(configuration, descriptor)) {
			kinds.addAll(configuration.kinds)
			true
		}
		else false
	}
	
	private fun matchConfiguration(configuration: ReflectConfiguration, descriptor: ClassDescriptor): Boolean {
		return when (configuration.target) {
			ReflectConfiguration.Target.CLASS      -> matchName(descriptor.fqNameSafe.asString(), configuration.name)
			ReflectConfiguration.Target.ANNOTATION -> descriptor.annotations.any { matchName(it.fqName!!.asString(), configuration.name) }
		}
	}
	
	private fun matchName(actual: String, required: String): Boolean {
		return actual.startsWith(required) && (actual.length == required.length || (actual.length > required.length && actual[required.length] == '.'))
	}
}