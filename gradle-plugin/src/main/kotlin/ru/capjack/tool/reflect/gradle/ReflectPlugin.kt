package ru.capjack.tool.reflect.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import ru.capjack.tool.reflect.gradle.internal.JsReflectExtensionImpl

open class ReflectPlugin : Plugin<Project> {
	companion object {
		const val NAME = "tool-reflect"
		const val ARTIFACT_GROUP = "ru.capjack.tool"
		const val ARTIFACT_NAME = "tool-reflect-gradle"
		
		val VERSION = this::class.java.classLoader.getResource("tool-reflect-version").readText()
		
		fun isJsApplicable(project: Project) : Boolean {
			return project.plugins.run {
				hasPlugin("kotlin2js") || hasPlugin("org.jetbrains.kotlin.multiplatform")
			}
		}
	}
	
	override fun apply(project: Project) {
		configureDefaultVersionsResolutionStrategy(project)
		if (isJsApplicable(project)) {
			project.extensions.create(JsReflectExtension::class, "jsReflect", JsReflectExtensionImpl::class)
		}
	}
	
	private fun configureDefaultVersionsResolutionStrategy(project: Project) {
		project.configurations.all {
			project.configurations.all {
				resolutionStrategy.eachDependency(Action {
					if (requested.group == ARTIFACT_GROUP && requested.name.startsWith(NAME) && requested.version.isNullOrEmpty()) {
						useVersion(VERSION)
					}
				})
			}
			resolutionStrategy.eachDependency(Action {
				if (requested.group == ARTIFACT_GROUP && requested.name.startsWith("tool-reflect-") && requested.version.isNullOrEmpty()) {
					useVersion(VERSION)
				}
			})
		}
	}
}
