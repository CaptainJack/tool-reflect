import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

plugins {
	kotlin("multiplatform") version "1.3.11"
	id("ru.capjack.ktjs-test") version "0.8.0"
	id("nebula.release") version "9.2.0"
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
			}
			compilations.getByName(KotlinCompilation.TEST_COMPILATION_NAME) {
				tasks.getByName<KotlinJsCompile>(compileKotlinTaskName) {
					val plugin = ":kt-reflect-js-gradle"
					evaluationDependsOn(plugin)
					val jar = project(plugin).tasks.getByName<Jar>("jar")
					dependsOn(jar)
					kotlinOptions.freeCompilerArgs += listOf(
						"-Xplugin=${jar.archiveFile.get().asFile.absolutePath}",
						"-P", "plugin:ru.capjack.kt-reflect-js:class=ru.capjack.kt.reflect.StubClassA",
						"-P", "plugin:ru.capjack.kt-reflect-js:class=ru.capjack.kt.reflect.StubClassB",
						"-P", "plugin:ru.capjack.kt-reflect-js:class=ru.capjack.kt.reflect.StubInterfaceA",
						
						"-P", "plugin:ru.capjack.kt-reflect-js:class=ru.capjack.kt.reflect.StubReflectName*",
						"-P", "plugin:ru.capjack.kt-reflect-js:class=ru.capjack.kt.reflect.StubReflectA:ANNOTATIONS",
						"-P", "plugin:ru.capjack.kt-reflect-js:class=ru.capjack.kt.reflect.StubReflectB:SUPERTYPES",
						"-P", "plugin:ru.capjack.kt-reflect-js:class=ru.capjack.kt.reflect.StubReflectC:CONSTRUCTOR",
						"-P", "plugin:ru.capjack.kt-reflect-js:class=ru.capjack.kt.reflect.StubReflectD:MEMBERS"
					)
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