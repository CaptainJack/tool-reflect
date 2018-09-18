package ru.capjack.lib.kt.reflect

import ru.capjack.lib.kt.reflect.internal.ref
import kotlin.reflect.KParameter
import kotlin.reflect.KType

actual val KParameter.typeRef: KType
	get() = ref.type