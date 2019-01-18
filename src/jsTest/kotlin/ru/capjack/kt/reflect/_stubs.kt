package ru.capjack.kt.reflect

open class StubReflectNameMask

@StubAnnotationB
class StubReflectA(val a: String) : StubReflectNameMask() {
	fun b() {}
}

@StubAnnotationB
class StubReflectB(val a: String) : StubReflectNameMask() {
	fun b() {}
}

@StubAnnotationB
class StubReflectC(val a: String) : StubReflectNameMask() {
	fun b() {}
}

@StubAnnotationB
class StubReflectD(val a: String) : StubReflectNameMask() {
	fun b() {}
}