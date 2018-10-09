package ru.capjack.kt.reflect

@StubAnnotationA("bbb")
class StubClassB(propertyA1: String) : @StubAnnotationB StubClassA(propertyA1), StubInterfaceA