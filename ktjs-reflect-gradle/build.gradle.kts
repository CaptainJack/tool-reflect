import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm")
	`java-gradle-plugin`
	`maven-publish`
	id("com.gradle.plugin-publish")
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	compileOnly(kotlin("gradle-plugin"))
	compileOnly(kotlin("compiler-embeddable"))
}

JavaVersion.VERSION_1_8.also {
	configure<JavaPluginConvention> { sourceCompatibility = it }
	tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = it.toString() }
}

gradlePlugin {
	(plugins) {
		"KtjsReflect" {
			id = "ru.capjack.ktjs-reflect"
			implementationClass = "ru.capjack.ktjs.reflect.gradle.ReflectPlugin"
		}
	}
}

pluginBundle {
	vcsUrl = "https://github.com/CaptainJack/ktjs-reflect"
	website = vcsUrl
	description = "Kotlin compiler plugin for support ktjs-reflect library"
	tags = listOf("kotlin", "javascript", "reflection")
	
	plugins["KtjsReflect"].displayName = "KtjsReflect plugin"
}

rootProject.tasks["postRelease"].dependsOn(tasks["publishPlugins"])

tasks.withType<ProcessResources> {
	inputs.property("version", version.toString())
	expand(project.properties)
}