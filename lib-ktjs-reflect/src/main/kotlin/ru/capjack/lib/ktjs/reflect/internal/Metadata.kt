package ru.capjack.gradle.ktjs.reflect.internal

import ru.capjack.gradle.ktjs.reflect.RMethod
import ru.capjack.gradle.ktjs.reflect.RParameter
import kotlin.reflect.KClass

internal object Metadata {
	fun fetchAnnotations(data: dynamic): List<Annotation> {
		if (data == null) {
			return emptyList()
		}
		
		return data.unsafeCast<Array<dynamic>>().map {
			(if (js("typeof it") == "function") {
				if (it.`$instance` == null) {
					it.`$instance` = js("new it()")
				}
				it.`$instance`
			}
			else it) as Annotation
		}
	}
	
	fun fetchParameters(data: dynamic): List<RParameter> {
		return data.unsafeCast<Array<dynamic>>().map { p ->
			RParameterImpl(
				p[0].unsafeCast<String>(),
				p[1].unsafeCast<JsClass<*>>().kotlin,
				fetchAnnotations(p[2])
			)
		}
	}
	
	
	fun <T : Any> fetchMethods(data: dynamic): Collection<RMethod<T, *>> {
		if (data == null) {
			return emptyList()
		}
		
		return data.unsafeCast<Array<dynamic>>().map { m ->
			RMethodImpl<T, Any>(
				m[0].unsafeCast<String>(),
				m[1].unsafeCast<String>(),
				fetchParameters(m[2]),
				(if (m[3] == null) Unit::class else m[3].unsafeCast<JsClass<*>>().kotlin).unsafeCast<KClass<Any>>(),
				fetchAnnotations(m[4])
			)
		}
	}
}

