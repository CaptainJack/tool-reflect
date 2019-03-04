package ru.capjack.tool.kt.reflect

class ReflectionException(target: Any) : RuntimeException("No reflection for '$target'")