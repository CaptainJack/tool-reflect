package ru.capjack.ktjs.reflect.gradle.compiler

open class ReflectConfigurationImpl(
	override val target: ReflectConfiguration.Target,
	override val name: String,
	override val kinds: List<ReflectKind>
) : ReflectConfiguration