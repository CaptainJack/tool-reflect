import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
	kotlin("platform.js")
	id("io.freefair.sources-jar")
	id("ru.capjack.capjack-bintray")
	id("ru.capjack.ktjs-test") version "0.1.0"
}

dependencies {
	expectedBy(project(":kt-reflect-common"))
	
	implementation(kotlin("stdlib-js"))
	
	testImplementation(kotlin("test-js"))
}

tasks.withType<Kotlin2JsCompile> {
	kotlinOptions {
		moduleKind = "umd"
		sourceMap = true
		sourceMapEmbedSources = "always"
	}
}

evaluationDependsOn(":kt-reflect-js-gradle")

tasks.getByName<KotlinJsCompile>("compileTestKotlin2Js") {
	val pluginJar = project(":kt-reflect-js-gradle").tasks.getByName<Jar>("jar")
	dependsOn(pluginJar)
	kotlinOptions.freeCompilerArgs += listOf(
		"-Xplugin=${pluginJar.archivePath.absolutePath}",
		"-P", "plugin:ru.capjack.kt-reflect-js:class=ru.capjack.lib.kt.reflect.StubClassA",
		"-P", "plugin:ru.capjack.kt-reflect-js:class=ru.capjack.lib.kt.reflect.StubClassB",
		"-P", "plugin:ru.capjack.kt-reflect-js:class=ru.capjack.lib.kt.reflect.StubInterfaceA"
	)
}