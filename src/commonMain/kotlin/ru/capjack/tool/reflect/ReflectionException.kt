package ru.capjack.tool.reflect

class ReflectionException(target: Any) : RuntimeException("No reflection for '$target'")