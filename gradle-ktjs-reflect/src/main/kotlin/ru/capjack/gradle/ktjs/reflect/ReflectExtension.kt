package ru.capjack.gradle.ktjs.reflect

import ru.capjack.gradle.ktjs.reflect.compiler.ReflectConfiguration

interface ReflectExtension {
	val configurations: List<ReflectConfiguration>
	
	fun withClass(name: String, vararg parts: ReflectConfiguration.Part)
	
	fun withAnnotation(name: String, vararg parts: ReflectConfiguration.Part)
}