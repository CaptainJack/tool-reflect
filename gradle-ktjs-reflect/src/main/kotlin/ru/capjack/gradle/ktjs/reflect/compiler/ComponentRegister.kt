package ru.capjack.gradle.ktjs.reflect.compiler

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.js.translate.extensions.JsSyntheticTranslateExtension

class ComponentRegister : ComponentRegistrar {
	override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
		val configurations = configuration.getList(CommandLineProcessor.CONFIG_CONFIGURATIONS)
		val messenger = configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)
		
		if (configurations.isNotEmpty()) {
			JsSyntheticTranslateExtension.registerExtension(project, ReflectJsSyntheticTranslateExtension(configurations, messenger))
		}
	}
}