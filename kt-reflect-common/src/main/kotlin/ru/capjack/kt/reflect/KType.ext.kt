package ru.capjack.kt.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KType

expect val KType.kClass: KClass<*>
