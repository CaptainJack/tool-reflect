package ru.capjack.kt.reflect.gradle

import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import ru.capjack.kt.reflect.gradle.internal.JsReflectTargetImpl

class JsCommandLineProcessor : CommandLineProcessor {
	
	companion object {
		val KEY_REFLECT_TARGETS: CompilerConfigurationKey<List<JsReflectTarget>> = CompilerConfigurationKey.create("JsReflectTargets")
	}
	
	override val pluginId = JsSubplugin.COMPILER_PLUGIN_ID
	
	override val pluginOptions: Collection<CliOption>
		get() = targetOptions
	
	private val targetOptions = JsReflectTarget.Type.values().map {
		val name = it.name.toLowerCase()
		CliOption(name, "<name>[:<unit>]*", "Reflect target with $name", false, true)
	}
	
	override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
		if (option in targetOptions) {
			val s = value.split(':')
			val target = JsReflectTargetImpl(
				s[0],
				JsReflectTarget.Type.valueOf(option.optionName.toUpperCase()),
				s.asSequence().drop(1).map { JsReflectTarget.Unit.valueOf(it.toUpperCase()) }.toList()
			)
			
			configuration.appendList(KEY_REFLECT_TARGETS, target)
		}
	}
}