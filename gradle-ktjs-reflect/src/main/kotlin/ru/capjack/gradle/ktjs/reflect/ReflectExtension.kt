package ru.capjack.gradle.ktjs.reflect

import ru.capjack.gradle.ktjs.reflect.compiler.ReflectConfiguration
import ru.capjack.gradle.ktjs.reflect.compiler.ReflectKind

interface ReflectExtension {
	val configurations: List<ReflectConfiguration>
	
	fun withClass(name: String, vararg kinds: ReflectKind)
	
	fun withAnnotation(name: String, vararg kinds: ReflectKind)
}