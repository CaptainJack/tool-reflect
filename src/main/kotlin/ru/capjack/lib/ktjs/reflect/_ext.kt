package ru.capjack.gradle.ktjs.reflect

import ru.capjack.gradle.ktjs.reflect.internal.RClassImpl
import kotlin.reflect.KClass

val <T : Any> T.reflection: RClass<T>
	get() = this::class.unsafeCast<KClass<T>>().reflection

val <T : Any> KClass<T>.reflection: RClass<T>
	get() {
		val metadata = this.js.asDynamic().`$metadata$`
		if (metadata.reflect !is RClassImpl<T>) {
			metadata.reflect = RClassImpl(this, metadata)
		}
		return metadata.reflect.unsafeCast<RClass<T>>()
	}

