plugins {
	kotlin("jvm")
	`java-gradle-plugin`
	`maven-publish`
	id("com.gradle.plugin-publish") version "0.10.1"
	id("ru.capjack.bintray")
}

repositories {
	gradlePluginPortal()
}

dependencies {
	compileOnly(kotlin("stdlib-jdk8"))
	compileOnly(kotlin("gradle-plugin"))
	compileOnly(kotlin("compiler-embeddable"))
	compileOnly("ru.capjack.gradle:gradle-depver:0.2.0")
}

gradlePlugin {
	plugins.create("Reflect") {
		id = "ru.capjack.reflect"
		implementationClass = "ru.capjack.tool.reflect.gradle.ReflectPlugin"
		displayName = "ru.capjack.reflect"
	}
}

pluginBundle {
	vcsUrl = "https://github.com/CaptainJack/tool-reflect"
	website = vcsUrl
	description = "Plugin for support tool-reflect library"
	tags = listOf("capjack", "kotlin", "reflect")
}

rootProject.tasks["postRelease"].dependsOn(tasks["publishPlugins"])

tasks.withType<ProcessResources> {
	inputs.property("version", version.toString())
	expand(project.properties)
}