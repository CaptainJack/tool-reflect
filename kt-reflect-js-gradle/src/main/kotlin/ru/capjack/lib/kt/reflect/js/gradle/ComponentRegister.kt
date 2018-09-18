package ru.capjack.lib.kt.reflect.js.gradle

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.js.translate.extensions.JsSyntheticTranslateExtension.Companion.registerExtension
import ru.capjack.lib.kt.reflect.js.gradle.internal.ReflectConfiguration
import ru.capjack.lib.kt.reflect.js.gradle.internal.ReflectJsSyntheticTranslateExtension

class ComponentRegister : ComponentRegistrar {
	override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
		val targets = configuration.getList(CommandLineProcessor.KEY_REFLECT_TARGETS)
		messenger = configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)
		
		if (targets.isNotEmpty()) {
			registerExtension(project, ReflectJsSyntheticTranslateExtension(ReflectConfiguration(targets)))
		}
	}
	
	companion object {
		lateinit var messenger: MessageCollector
	}
}

fun log(vararg a: Any?) {
	ComponentRegister.messenger.report(CompilerMessageSeverity.ERROR, System.nanoTime().toString() + " " + a.joinToString(" "))
}