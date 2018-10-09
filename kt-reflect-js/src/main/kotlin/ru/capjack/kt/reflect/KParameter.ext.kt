package ru.capjack.kt.reflect

import ru.capjack.kt.reflect.internal.ref
import kotlin.reflect.KParameter
import kotlin.reflect.KType

actual val KParameter.indexRef: Int
	get() = ref.index

actual val KParameter.nameRef: String?
	get() = ref.name

actual val KParameter.typeRef: KType
	get() = ref.type