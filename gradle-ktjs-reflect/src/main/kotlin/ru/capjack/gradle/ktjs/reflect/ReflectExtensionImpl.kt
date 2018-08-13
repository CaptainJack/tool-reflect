package ru.capjack.gradle.ktjs.reflect

import ru.capjack.gradle.ktjs.reflect.compiler.ReflectConfiguration
import ru.capjack.gradle.ktjs.reflect.compiler.ReflectConfigurationImpl

open class ReflectExtensionImpl : ReflectExtension {
	override val configurations: MutableList<ReflectConfiguration> = mutableListOf()
	
	override fun withClass(name: String, vararg parts: ReflectConfiguration.Part) {
		configurations.add(
			ReflectConfigurationImpl(
				ReflectConfiguration.Target.CLASS,
				name,
				parts.toList()
			)
		)
	}
	
	override fun withAnnotation(name: String, vararg parts: ReflectConfiguration.Part) {
		configurations.add(
			ReflectConfigurationImpl(
				ReflectConfiguration.Target.ANNOTATION,
				name,
				parts.toList()
			)
		)
	}
}