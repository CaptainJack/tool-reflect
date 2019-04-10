package ru.capjack.tool.reflect.gradle

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.js.translate.extensions.JsSyntheticTranslateExtension.Companion.registerExtension
import ru.capjack.tool.reflect.gradle.internal.JsReflectConfiguration
import ru.capjack.tool.reflect.gradle.internal.JsReflectSyntheticTranslateExtension

class JsComponentRegister : ComponentRegistrar {
	override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
		val targets = configuration.getList(JsCommandLineProcessor.KEY_REFLECT_TARGETS)
		
		if (targets.isNotEmpty()) {
			registerExtension(project,
				JsReflectSyntheticTranslateExtension(JsReflectConfiguration(targets))
			)
		}
	}
}