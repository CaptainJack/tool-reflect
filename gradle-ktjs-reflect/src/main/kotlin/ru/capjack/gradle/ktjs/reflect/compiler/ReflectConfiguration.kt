package ru.capjack.gradle.ktjs.reflect.compiler

interface ReflectConfiguration {
	val target: Target
	val name: String
	val parts: List<Part>
	
	enum class Target {
		CLASS, ANNOTATION
	}
	
	enum class Part {
		CONSTRUCTOR, METHODS
	}
}

