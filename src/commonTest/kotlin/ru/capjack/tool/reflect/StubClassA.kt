package ru.capjack.tool.reflect

@Suppress("unused")
open class StubClassA(override val propertyA1: String) : StubInterfaceA {
	override fun methodA1(param1: Int): String {
		return param1.toString()
	}
	
	fun methodA2(): StubClassC {
		return StubClassC()
	}
}


