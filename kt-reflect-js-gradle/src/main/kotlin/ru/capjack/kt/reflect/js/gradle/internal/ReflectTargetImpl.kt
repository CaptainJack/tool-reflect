package ru.capjack.kt.reflect.js.gradle.internal

import ru.capjack.kt.reflect.js.gradle.ReflectTarget

internal class ReflectTargetImpl(
	override val name: String,
	override val type: ReflectTarget.Type,
	override val units: Set<ReflectTarget.Unit>
) : ReflectTarget {
	constructor(name: String, type: ReflectTarget.Type, units: List<ReflectTarget.Unit>) : this(name, type, units.toSet())
}