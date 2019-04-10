package ru.capjack.tool.reflect.gradle

interface JsReflectExtension {
	val targets: List<JsReflectTarget>
	
	fun withClass(name: String, vararg units: JsReflectTarget.Unit)
	
	fun withAnnotation(name: String, vararg units: JsReflectTarget.Unit)
}