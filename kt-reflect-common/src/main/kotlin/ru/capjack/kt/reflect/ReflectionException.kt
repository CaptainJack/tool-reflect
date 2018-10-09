package ru.capjack.kt.reflect

class ReflectionException(target: Any) : RuntimeException("No reflection for '$target'")