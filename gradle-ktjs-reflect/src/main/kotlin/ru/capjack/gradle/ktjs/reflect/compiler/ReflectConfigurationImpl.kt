package ru.capjack.gradle.ktjs.reflect.compiler

open class ReflectConfigurationImpl(
	override val target: ReflectConfiguration.Target,
	override val name: String,
	override val kinds: List<ReflectKind>
) : ReflectConfiguration