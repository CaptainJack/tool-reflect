package ru.capjack.ktjs.reflect.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

open class ReflectPlugin : Plugin<Project> {
	companion object {
		val VERSION = this::class.java.classLoader.getResource("ktjs-reflect-version").readText()
		
		fun isEnabled(project: Project) = project.plugins.hasPlugin(ReflectPlugin::class.java)
	}
	
	override fun apply(project: Project) {
		configureDefaultVersionsResolutionStrategy(project)
		
		project.extensions.create(ReflectExtension::class.java,"reflect", ReflectExtensionImpl::class.java)
	}
	
	private fun configureDefaultVersionsResolutionStrategy(project: Project) {
		
		project.configurations.all { configuration ->
			configuration.resolutionStrategy.eachDependency { details ->
				val requested = details.requested
				if (requested.group == "ru.capjack.lib" && requested.name == "lib-ktjs-reflect" && requested.version.isEmpty()) {
					details.useVersion(VERSION)
				}
			}
		}
	}
}
