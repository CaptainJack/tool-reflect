package ru.capjack.lib.kt.reflect.js.gradle.internal

import ru.capjack.lib.kt.reflect.js.gradle.ReflectExtension
import ru.capjack.lib.kt.reflect.js.gradle.ReflectTarget

internal class ReflectExtensionImpl : ReflectExtension {
	override val targets = mutableListOf<ReflectTarget>()
	
	override fun withClass(name: String, vararg units: ReflectTarget.Unit) {
		addTarget(name, ReflectTarget.Type.CLASS, units.toList())
	}
	
	override fun withAnnotation(name: String, vararg units: ReflectTarget.Unit) {
		addTarget(name, ReflectTarget.Type.ANNOTATION, units.toList())
	}
	
	private fun addTarget(name: String, type: ReflectTarget.Type, units: List<ReflectTarget.Unit>) {
		targets.add(ReflectTargetImpl(name, type, units))
	}
}