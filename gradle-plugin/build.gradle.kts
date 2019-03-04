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
	plugins.create("KtReflect") {
		id = "ru.capjack.kt-reflect"
		implementationClass = "ru.capjack.tool.kt.reflect.gradle.ReflectPlugin"
		displayName = "kt-reflect"
	}
}

pluginBundle {
	vcsUrl = "https://github.com/CaptainJack/kt-reflect"
	website = vcsUrl
	description = "Plugin for support kt-reflect library"
	tags = listOf("capjack", "kotlin", "reflection")
}

rootProject.tasks["postRelease"].dependsOn(tasks["publishPlugins"])

tasks.withType<ProcessResources> {
	inputs.property("version", version.toString())
	expand(project.properties)
}