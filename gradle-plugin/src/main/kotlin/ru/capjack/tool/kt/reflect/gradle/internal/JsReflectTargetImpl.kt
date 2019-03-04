package ru.capjack.tool.kt.reflect.gradle.internal

import ru.capjack.tool.kt.reflect.gradle.JsReflectTarget

internal class JsReflectTargetImpl(
	override val name: String,
	override val type: JsReflectTarget.Type,
	override val units: Set<JsReflectTarget.Unit>
) : JsReflectTarget {
	constructor(name: String, type: JsReflectTarget.Type, units: List<JsReflectTarget.Unit>) : this(name, type, units.toSet())
}