package ru.capjack.lib.kt.reflect

import ru.capjack.lib.kt.reflect.internal.ref
import kotlin.reflect.KClass
import kotlin.reflect.KType

actual val KType.kClass: KClass<*>
	get() = ref.kClass