import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
	kotlin("multiplatform") version "1.3.10"
	id("ru.capjack.ktjs-test") version "0.8.0"
	id("nebula.release") version "9.1.2"
	id("ru.capjack.capjack-bintray") version "0.13.0"
}


allprojects {
	group = "ru.capjack.kt.reflect"
	repositories {
		jcenter()
	}
}

afterEvaluate {
	// https://youtrack.jetbrains.com/issue/KT-29058
	publishing.publications.forEach { (it as MavenPublication).groupId = group.toString() }
}

kotlin {
	targets {
		add(presets["jvm"].createTarget("jvm").apply {
			compilations.all {
				tasks.getByName<KotlinJvmCompile>(compileKotlinTaskName).kotlinOptions.jvmTarget = "1.8"
			}
		})
		
		add(presets["js"].createTarget("js").apply {
			compilations.all {
				tasks.getByName<KotlinJsCompile>(compileKotlinTaskName).kotlinOptions {
					sourceMap = true
					sourceMapEmbedSources = "always"
					moduleKind = "umd"
				}
				tasks.getByName<KotlinJsCompile>("compileTestKotlinJs") {
				}
			}
		})
	}
	
	sourceSets {
		commonMain {
			dependencies {
				implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
			}
		}
		commonTest {
			dependencies {
				implementation("org.jetbrains.kotlin:kotlin-test-common")
				implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
			}
		}
		
		named("jvmMain") {
			dependencies {
				implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
				implementation("org.jetbrains.kotlin:kotlin-reflect")
			}
		}
		named("jvmTest") {
			dependencies {
				implementation("org.jetbrains.kotlin:kotlin-test-junit")
			}
		}
		
		named("jsMain") {
			dependencies {
				implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
			}
		}
		named("jsTest") {
			dependencies {
				implementation("org.jetbrains.kotlin:kotlin-test-js")
			}
		}
	}
}