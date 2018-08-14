package ru.capjack.ktjs.reflect.gradle.compiler

import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

class CommandLineProcessor : CommandLineProcessor {
	
	companion object {
		val OPTION_CLASS = CliOption(Const.ARG_CLASS, "<name>", "Class name mask", false, true)
		val OPTION_ANNOTATION = CliOption(Const.ARG_ANNOTATION, "<name>", "Annotation name mask", false, true)
		
		val CONFIG_CONFIGURATIONS: CompilerConfigurationKey<List<ReflectConfiguration>> = CompilerConfigurationKey.create("reflect configurations")
	}
	
	override val pluginId = Const.PLUGIN_ID
	
	override val pluginOptions: Collection<CliOption> = listOf(
		OPTION_CLASS,
		OPTION_ANNOTATION
	)
	
	override fun processOption(option: CliOption, value: String, configuration: CompilerConfiguration) {
		if (option == OPTION_CLASS || option == OPTION_ANNOTATION) {
			val s1 = value.split('|', limit = 2)
			if (s1.isNotEmpty()) {
				val name = s1[0]
				val parts = if (s1.size == 2 && s1[1].isNotBlank()) {
					s1[1].split(';')
				}
				else {
					emptyList()
				}
				
				configuration.appendList(
					CONFIG_CONFIGURATIONS,
					ReflectConfigurationImpl(
						option.target,
						name,
						parts.map { ReflectKind.valueOf(it.toUpperCase()) })
				)
			}
		}
	}
	
	private val CliOption.target
		get() = when (this) {
			OPTION_CLASS -> ReflectConfiguration.Target.CLASS
			OPTION_ANNOTATION -> ReflectConfiguration.Target.ANNOTATION
			else -> throw IllegalArgumentException()
			
		}
}