package ru.capjack.gradle.ktjs.reflect.compiler

import org.jetbrains.kotlin.cli.common.messages.MessageCollector
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
import ru.capjack.gradle.ktjs.reflect.compiler.CodeGenerator.generateAnnotations
import ru.capjack.gradle.ktjs.reflect.compiler.CodeGenerator.generateConstructor
import ru.capjack.gradle.ktjs.reflect.compiler.CodeGenerator.generateMethods
import ru.capjack.gradle.ktjs.reflect.compiler.CodeGenerator.jsObject
import ru.capjack.gradle.ktjs.reflect.compiler.CodeGenerator.jsProperty

class ReflectJsSyntheticTranslateExtension(
	private val configurations: List<ReflectConfiguration>,
	private val messenger: MessageCollector
) : JsSyntheticTranslateExtension {
	
	override fun generateClassSyntheticParts(declaration: KtPureClassOrObject, descriptor: ClassDescriptor, translator: DeclarationBodyVisitor, context: TranslationContext) {
		var reflect = false
		val parts = mutableSetOf<ReflectConfiguration.Part>()
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
					ReflectConfiguration.Part.CONSTRUCTOR ->
						metadata.add(jsProperty("constructor", generateConstructor(descriptor, context)))
					ReflectConfiguration.Part.METHODS     ->
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
			
			descriptor.annotations
		}
	}
	
	private fun collectParts(parts: MutableCollection<ReflectConfiguration.Part>, configuration: ReflectConfiguration, descriptor: ClassDescriptor): Boolean {
		return if (matchConfiguration(configuration, descriptor)) {
			parts.addAll(configuration.parts)
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