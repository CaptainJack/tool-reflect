package ru.capjack.tool.kt.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure

actual inline val KType.kClass: KClass<*>
	get() = jvmErasure