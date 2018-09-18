import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("platform.jvm")
	id("io.freefair.sources-jar")
	id("ru.capjack.capjack-bintray")
}

dependencies {
	expectedBy(project(":kt-reflect-common"))
	
	implementation(kotlin("stdlib-jdk8"))
	implementation(kotlin("reflect"))
	
	testImplementation(kotlin("test-junit"))
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "1.8"
}