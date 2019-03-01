package ru.capjack.kt.reflect.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import ru.capjack.kt.reflect.gradle.internal.JsReflectExtensionImpl

open class ReflectPlugin : Plugin<Project> {
	companion object {
		const val ARTIFACT_GROUP = "ru.capjack.kt"
		const val ARTIFACT_NAME = "kt-reflect-gradle"
		
		val VERSION = this::class.java.classLoader.getResource("kt-reflect-version").readText()
		
		fun isJsApplicable(project: Project) : Boolean {
			return project.plugins.run {
				hasPlugin("kotlin2js") || hasPlugin("org.jetbrains.kotlin.multiplatform")
			}
		}
	}
	
	override fun apply(project: Project) {
		configureDefaultVersionsResolutionStrategy(project)
		if (isJsApplicable(project)) {
			project.extensions.create(JsReflectExtension::class, "ktReflectJs", JsReflectExtensionImpl::class)
		}
	}
	
	private fun configureDefaultVersionsResolutionStrategy(project: Project) {
		project.configurations.all {
			resolutionStrategy.eachDependency(Action {
				if (requested.group == ARTIFACT_GROUP && requested.name.startsWith("kt-reflect-") && requested.version.isNullOrEmpty()) {
					useVersion(VERSION)
				}
			})
		}
	}
}
