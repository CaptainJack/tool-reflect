package ru.capjack.ktjs.reflect.gradle.compiler

import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.js.translate.extensions.JsSyntheticTranslateExtension

class ComponentRegister : ComponentRegistrar {
	override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
		val configurations = configuration.getList(CommandLineProcessor.CONFIG_CONFIGURATIONS)
		
		if (configurations.isNotEmpty()) {
			JsSyntheticTranslateExtension.registerExtension(project, JsSyntheticTranslateExtension(configurations))
		}
	}
}