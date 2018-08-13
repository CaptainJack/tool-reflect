package ru.capjack.gradle.ktjs.reflect.compiler

open class ReflectConfigurationImpl(
	override val target: ReflectConfiguration.Target,
	override val name: String,
	override val parts: List<ReflectConfiguration.Part>
) : ReflectConfiguration