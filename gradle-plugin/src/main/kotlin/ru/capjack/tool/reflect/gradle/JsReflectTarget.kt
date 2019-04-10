package ru.capjack.tool.reflect.gradle

interface JsReflectTarget {
	val type: Type
	val name: String
	val units: Set<Unit>
	
	enum class Type {
		CLASS, ANNOTATION
	}
	
	enum class Unit {
		ANNOTATIONS,
		SUPERTYPES,
		CONSTRUCTOR,
		MEMBERS
	}
}

