package ru.capjack.gradle.ktjs.reflect.internal

import ru.capjack.gradle.ktjs.reflect.RParameter
import kotlin.reflect.KClass

class RParameterImpl(
	override val name: String,
	override val type: KClass<*>,
	override val annotations: List<Annotation>
) : RParameter