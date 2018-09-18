import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	`kotlin-dsl`
	`java-gradle-plugin`
	`maven-publish`
	id("com.gradle.plugin-publish") version "0.10.0"
}

dependencies {
	compileOnly(kotlin("gradle-plugin"))
	compileOnly(kotlin("compiler-embeddable"))
}

gradlePlugin {
	plugins.create("KtReflectJs") {
		id = "ru.capjack.lib.kt-reflect-js"
		implementationClass = "ru.capjack.lib.kt.reflect.js.gradle.ReflectPlugin"
		displayName = "Lib KtReflectJs"
	}
}

pluginBundle {
	vcsUrl = "https://github.com/CaptainJack/lib-kt-reflect"
	website = vcsUrl
	description = "Kotlin compiler plugin for support kt-reflect-js library"
	tags = listOf("kotlin", "javascript", "reflection")
}

rootProject.tasks["postRelease"].dependsOn(tasks["publishPlugins"])

tasks.withType<ProcessResources> {
	inputs.property("version", version.toString())
	expand(project.properties)
}