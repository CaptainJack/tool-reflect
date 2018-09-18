package ru.capjack.lib.kt.reflect

import kotlin.reflect.KParameter
import kotlin.reflect.KType

actual inline val KParameter.typeRef: KType
	get() = type