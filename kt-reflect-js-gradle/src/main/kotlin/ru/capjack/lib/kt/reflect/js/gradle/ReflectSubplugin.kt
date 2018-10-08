package ru.capjack.lib.kt.reflect.js.gradle

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.compile.AbstractCompile
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.plugin.KotlinGradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class ReflectSubplugin : KotlinGradleSubplugin<AbstractCompile> {
	
	override fun isApplicable(project: Project, task: AbstractCompile): Boolean {
		return ReflectPlugin.isEnabled(project)
	}
	
	override fun getCompilerPluginId(): String {
		return Const.PLUGIN
	}
	
	override fun getPluginArtifact(): SubpluginArtifact {
		return SubpluginArtifact(Const.GROUP, Const.ARTIFACT_PLUGIN, ReflectPlugin.VERSION)
	}
	
	override fun apply(
		project: Project,
		kotlinCompile: AbstractCompile,
		javaCompile: AbstractCompile,
		variantData: Any?,
		androidProjectHandler: Any?,
		javaSourceSet: SourceSet?
	): List<SubpluginOption> {
		
		if (!ReflectPlugin.isEnabled(project)) {
			return emptyList()
		}
		
		val extension = project.extensions.findByType<ReflectExtension>()
			?: return emptyList()
		
		val options = mutableListOf<SubpluginOption>()
		
		for (target in extension.targets) {
			options.add(SubpluginOption(target.optionName, target.optionValue))
		}
		
		return options
	}
	
	private val ReflectTarget.optionName: String
		get() = type.name.toLowerCase()
	
	private val ReflectTarget.optionValue: String
		get() = listOf(name).asSequence().plus(units.map { it.name.toLowerCase() }).joinToString(":")
	
}
