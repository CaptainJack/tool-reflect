package ru.capjack.tool.reflect

import ru.capjack.tool.reflect.internal.ref
import kotlin.reflect.KClass
import kotlin.reflect.KType

actual val KType.kClass: KClass<*>
	get() = ref.kClass