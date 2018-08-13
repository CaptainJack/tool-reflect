package ru.capjack.gradle.ktjs.reflect

import ru.capjack.gradle.ktjs.reflect.compiler.ReflectKind
import ru.capjack.gradle.ktjs.reflect.compiler.ReflectConfiguration
import ru.capjack.gradle.ktjs.reflect.compiler.ReflectConfigurationImpl

open class ReflectExtensionImpl : ReflectExtension {
	override val configurations: MutableList<ReflectConfiguration> = mutableListOf()
	
	override fun withClass(name: String, vararg kinds: ReflectKind) {
		configurations.add(
			ReflectConfigurationImpl(
				ReflectConfiguration.Target.CLASS,
				name,
				kinds.toList()
			)
		)
	}
	
	override fun withAnnotation(name: String, vararg kinds: ReflectKind) {
		configurations.add(
			ReflectConfigurationImpl(
				ReflectConfiguration.Target.ANNOTATION,
				name,
				kinds.toList()
			)
		)
	}
}