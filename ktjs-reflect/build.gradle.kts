import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
	id("kotlin2js")
	id("io.freefair.sources-jar")
	id("ru.capjack.capjack-publish")
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