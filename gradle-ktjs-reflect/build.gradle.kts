import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm")
	id("ru.capjack.kotlin-sources-jar")
	id("ru.capjack.capjack-publish")
	`java-gradle-plugin`
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
			implementationClass = "ru.capjack.gradle.ktjs.reflect.ReflectPlugin"
		}
	}
}

tasks.withType<ProcessResources> {
	inputs.property("version", version.toString())
	expand(project.properties)
}

