package ru.capjack.tool.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KType

expect val KType.kClass: KClass<*>
