package ru.capjack.tool.kt.reflect

import kotlin.reflect.KParameter
import kotlin.reflect.KType

expect val KParameter.indexRef: Int

expect val KParameter.nameRef: String?

expect val KParameter.typeRef: KType