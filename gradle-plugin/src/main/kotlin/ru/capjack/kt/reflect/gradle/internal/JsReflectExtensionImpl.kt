package ru.capjack.kt.reflect.gradle.internal

import ru.capjack.kt.reflect.gradle.JsReflectExtension
import ru.capjack.kt.reflect.gradle.JsReflectTarget

internal open class JsReflectExtensionImpl : JsReflectExtension {
	override val targets = mutableListOf<JsReflectTarget>()
	
	override fun withClass(name: String, vararg units: JsReflectTarget.Unit) {
		addTarget(name, JsReflectTarget.Type.CLASS, units.toList())
	}
	
	override fun withAnnotation(name: String, vararg units: JsReflectTarget.Unit) {
		addTarget(name, JsReflectTarget.Type.ANNOTATION, units.toList())
	}
	
	private fun addTarget(name: String, type: JsReflectTarget.Type, units: List<JsReflectTarget.Unit>) {
		targets.add(JsReflectTargetImpl(name, type, units))
	}
}