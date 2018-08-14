package ru.capjack.ktjs.reflect.internal

import ru.capjack.ktjs.reflect.RParameter
import kotlin.reflect.KClass

class RParameterImpl(
	override val name: String,
	override val type: KClass<*>,
	override val annotations: List<Annotation>
) : RParameter