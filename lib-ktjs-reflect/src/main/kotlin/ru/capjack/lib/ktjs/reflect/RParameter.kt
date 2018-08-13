package ru.capjack.gradle.ktjs.reflect

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

interface RParameter : KAnnotatedElement {
	val name: String
	val type: KClass<out Any>
}