package ru.capjack.lib.kt.reflect.internal

import kotlin.reflect.KAnnotatedElement

internal abstract class KAnnotatedElementRef(
	override val annotations: List<Annotation>
) : KAnnotatedElement