package ru.capjack.tool.reflect.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.capjack.gradle.depver.DepverExtension
import ru.capjack.tool.reflect.gradle.internal.JsReflectExtensionImpl

open class ReflectPlugin : Plugin<Project> {
	companion object {
		const val NAME = "tool-reflect"
		const val ARTIFACT_GROUP = "ru.capjack.tool"
		const val ARTIFACT_NAME = "tool-reflect-gradle"
		
		val VERSION = this::class.java.classLoader.getResource("$NAME-version")!!.readText()
	}
	
	override fun apply(project: Project) {
		(project.extensions.findByName("depver") as? DepverExtension)?.set(ARTIFACT_GROUP, NAME, VERSION)
		
		project.extensions.create(JsReflectExtension::class.java, "jsReflect", JsReflectExtensionImpl::class.java)
	}
}
