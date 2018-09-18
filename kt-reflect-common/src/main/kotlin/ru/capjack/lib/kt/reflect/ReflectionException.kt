package ru.capjack.lib.kt.reflect

class ReflectionException(target: Any) : RuntimeException("No reflection for '$target'")