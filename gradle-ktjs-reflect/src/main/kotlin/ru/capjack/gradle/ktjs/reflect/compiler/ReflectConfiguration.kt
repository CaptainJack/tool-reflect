package ru.capjack.gradle.ktjs.reflect.compiler

interface ReflectConfiguration {
	val target: Target
	val name: String
	val kinds: List<ReflectKind>
	
	enum class Target {
		CLASS, ANNOTATION
	}
}

