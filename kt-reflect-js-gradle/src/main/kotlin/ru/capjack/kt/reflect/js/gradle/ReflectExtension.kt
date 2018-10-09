package ru.capjack.kt.reflect.js.gradle

interface ReflectExtension {
	val targets: List<ReflectTarget>
	
	fun withClass(name: String, vararg units: ReflectTarget.Unit)
	
	fun withAnnotation(name: String, vararg units: ReflectTarget.Unit)
}