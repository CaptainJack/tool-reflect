import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "ru.capjack.gradle"

plugins {
	kotlin("jvm")
	id("ru.capjack.kotlin-sources-jar")
	id("ru.capjack.capjack-publish")
	`java-gradle-plugin`
	id("com.gradle.plugin-publish") version "0.10.0"
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

capjackPublish {
	githubRepository = "lib-ktjs-reflect"
}

gradlePlugin {
	(plugins) {
		"KtjsReflect" {
			id = "ru.capjack.ktjs-reflect"
			implementationClass = "ru.capjack.gradle.ktjs.reflect.ReflectPlugin"
		}
	}
}

pluginBundle {
	vcsUrl = "https://github.com/CaptainJack/lib-ktjs-reflect"
	website = vcsUrl
	description = "Kotlin compiler plugin for support lib-ktjs-reflect library"
	tags = listOf("capjack")
	
	plugins["KtjsReflect"].displayName = "KtjsReflect plugin"
}

parent!!.tasks.getByName("postRelease").dependsOn("publishPlugins")

tasks.withType<ProcessResources> {
	inputs.property("version", version.toString())
	expand(project.properties)
}

