plugins {
	`kotlin-dsl`
	`java-gradle-plugin`
	`maven-publish`
	id("com.gradle.plugin-publish") version "0.10.1"
}

dependencies {
	compileOnly(kotlin("gradle-plugin"))
	compileOnly(kotlin("compiler-embeddable"))
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