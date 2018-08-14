package ru.capjack.ktjs.reflect.gradle

import ru.capjack.ktjs.reflect.gradle.compiler.ReflectKind
import ru.capjack.ktjs.reflect.gradle.compiler.ReflectConfiguration
import ru.capjack.ktjs.reflect.gradle.compiler.ReflectConfigurationImpl

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