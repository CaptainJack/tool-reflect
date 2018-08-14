package ru.capjack.ktjs.reflect.gradle

import ru.capjack.ktjs.reflect.gradle.compiler.ReflectConfiguration
import ru.capjack.ktjs.reflect.gradle.compiler.ReflectKind

interface ReflectExtension {
	val configurations: List<ReflectConfiguration>
	
	fun withClass(name: String, vararg kinds: ReflectKind)
	
	fun withAnnotation(name: String, vararg kinds: ReflectKind)
}