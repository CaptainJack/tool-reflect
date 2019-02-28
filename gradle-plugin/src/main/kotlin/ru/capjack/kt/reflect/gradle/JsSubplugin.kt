package ru.capjack.kt.reflect.gradle

import org.gradle.api.Project
import org.gradle.api.tasks.compile.AbstractCompile
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinGradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class JsSubplugin : KotlinGradleSubplugin<AbstractCompile> {
	companion object {
		const val COMPILER_PLUGIN_ID = "ru.capjack.kt-reflect"
	}
	
	override fun isApplicable(project: Project, task: AbstractCompile): Boolean {
		return ReflectPlugin.isJsApplicable(project)
	}
	
	override fun getCompilerPluginId(): String {
		return COMPILER_PLUGIN_ID
	}
	
	override fun getPluginArtifact(): SubpluginArtifact {
		return SubpluginArtifact(
			ReflectPlugin.ARTIFACT_GROUP,
			ReflectPlugin.ARTIFACT_NAME,
			ReflectPlugin.VERSION
		)
	}
	
	override fun apply(
		project: Project,
		kotlinCompile: AbstractCompile,
		javaCompile: AbstractCompile?,
		variantData: Any?,
		androidProjectHandler: Any?,
		kotlinCompilation: KotlinCompilation<KotlinCommonOptions>?
	): List<SubpluginOption> {
		
		if (!ReflectPlugin.isJsApplicable(project)) {
			return emptyList()
		}
		
		val extension = project.extensions.findByType<JsReflectExtension>()
			?: return emptyList()
		
		val options = mutableListOf<SubpluginOption>()
		
		for (target in extension.targets) {
			options.add(SubpluginOption(target.optionName, target.optionValue))
		}
		
		return options
	}
	
	private val JsReflectTarget.optionName: String
		get() = type.name.toLowerCase()
	
	private val JsReflectTarget.optionValue: String
		get() = listOf(name).asSequence().plus(units.map { it.name.toLowerCase() }).joinToString(":")
	
}
