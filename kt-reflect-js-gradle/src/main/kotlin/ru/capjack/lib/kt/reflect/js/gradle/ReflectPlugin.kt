package ru.capjack.lib.kt.reflect.js.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.hasPlugin
import ru.capjack.lib.kt.reflect.js.gradle.internal.ReflectExtensionImpl

open class ReflectPlugin : Plugin<Project> {
	companion object {
		val VERSION = this::class.java.classLoader.getResource("kt-reflect-version").readText()
		
		fun isEnabled(project: Project) = project.plugins.hasPlugin(ReflectPlugin::class)
	}
	
	override fun apply(project: Project) {
		configureDefaultVersionsResolutionStrategy(project)
		
		if (isEnabled(project)) {
			project.extensions.create(ReflectExtension::class, "ktReflect", ReflectExtensionImpl::class)
		}
	}
	
	private fun configureDefaultVersionsResolutionStrategy(project: Project) {
		project.configurations.forEach { configuration ->
			configuration.resolutionStrategy.eachDependency(Action {
				if (requested.group == Const.GROUP && requested.name == Const.ARTIFACT_LIB && requested.version.isNullOrEmpty()) {
					useVersion(VERSION)
				}
			})
		}
	}
}
