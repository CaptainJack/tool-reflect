import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

group = "ru.capjack.lib"

plugins {
	id("kotlin2js") version "1.2.60"
	id("nebula.release") version "6.3.5"
	id("ru.capjack.kotlin-sources-jar") version "0.3.0"
	id("ru.capjack.capjack-publish") version "0.6.0"
}

allprojects {
	repositories {
		jcenter()
	}
}

dependencies {
	implementation(kotlin("stdlib-js"))
}

tasks.withType<Kotlin2JsCompile> {
	kotlinOptions {
		moduleKind = "amd"
		sourceMap = true
		sourceMapEmbedSources = "always"
	}
}