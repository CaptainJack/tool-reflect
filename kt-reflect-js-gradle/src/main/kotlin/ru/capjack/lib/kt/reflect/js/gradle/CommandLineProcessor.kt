package ru.capjack.lib.kt.reflect.js.gradle

import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import ru.capjack.lib.kt.reflect.js.gradle.internal.ReflectTargetImpl

class CommandLineProcessor : CommandLineProcessor {
	
	companion object {
		val KEY_REFLECT_TARGETS: CompilerConfigurationKey<List<ReflectTarget>> = CompilerConfigurationKey.create("ReflectTarget")
	}
	
	override val pluginId = Const.PLUGIN
	
	override val pluginOptions: Collection<CliOption>
		get() = targetOptions
	
	private val targetOptions = ReflectTarget.Type.values().map {
		val name = it.name.toLowerCase()
		CliOption(name, "<name>[:<unit>]*", "Reflect target with $name", false, true)
	}
	
	override fun processOption(option: CliOption, value: String, configuration: CompilerConfiguration) {
		if (option in targetOptions) {
			val s = value.split(':')
			val target = ReflectTargetImpl(
				s[0],
				ReflectTarget.Type.valueOf(option.name.toUpperCase()),
				s.asSequence().drop(1).map { ReflectTarget.Unit.valueOf(it.toUpperCase()) }.toList()
			)
			
			configuration.appendList(KEY_REFLECT_TARGETS, target)
		}
	}
}