import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants.MODULE_UMD
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation.Companion.MAIN_COMPILATION_NAME
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation.Companion.TEST_COMPILATION_NAME

plugins {
	kotlin("multiplatform") version "1.3.21"
	id("nebula.release") version "10.0.1"
	id("ru.capjack.bintray") version "0.17.0"
	id("ru.capjack.kojste") version "0.11.0"
}


allprojects {
	group = "ru.capjack.tool"
	repositories {
		jcenter()
	}
}

capjackBintray {
	publications(":", ":tool-reflect-gradle")
}

kotlin {
	sourceSets {
		commonMain {
			dependencies {
				implementation(kotlin("stdlib-common"))
			}
		}
		commonTest {
			dependencies {
				implementation(kotlin("test-common"))
				implementation(kotlin("test-annotations-common"))
			}
		}
	}
	
	
	jvm().compilations {
		all {
			kotlinOptions.jvmTarget = "1.8"
		}
		
		get(MAIN_COMPILATION_NAME).defaultSourceSet {
			dependencies {
				implementation(kotlin("stdlib-jdk8"))
				implementation(kotlin("reflect"))
			}
		}
		
		get(TEST_COMPILATION_NAME).defaultSourceSet {
			dependencies {
				implementation(kotlin("test-junit"))
			}
		}
	}
	
	js().compilations {
		all {
			kotlinOptions.moduleKind = MODULE_UMD
		}
		
		get(MAIN_COMPILATION_NAME).defaultSourceSet {
			dependencies {
				implementation(kotlin("stdlib-js"))
			}
		}
		
		get(TEST_COMPILATION_NAME).defaultSourceSet {
			dependencies {
				implementation(kotlin("test-js"))
			}
		}
		
		get(TEST_COMPILATION_NAME).compileKotlinTask.apply {
			val plugin = ":tool-reflect-gradle"
			evaluationDependsOn(plugin)
			val jar = project(plugin).tasks.getByName<Jar>("jar")
			dependsOn(jar)
			kotlinOptions.freeCompilerArgs += listOf(
				"-Xplugin=${jar.archiveFile.get().asFile.absolutePath}",
				
				"-P", "plugin:ru.capjack.tool.reflect:class=ru.capjack.tool.reflect.StubClassA",
				"-P", "plugin:ru.capjack.tool.reflect:class=ru.capjack.tool.reflect.StubClassB",
				"-P", "plugin:ru.capjack.tool.reflect:class=ru.capjack.tool.reflect.StubInterfaceA",
				
				"-P", "plugin:ru.capjack.tool.reflect:class=ru.capjack.tool.reflect.StubReflectName*",
				"-P", "plugin:ru.capjack.tool.reflect:class=ru.capjack.tool.reflect.StubReflectA:ANNOTATIONS",
				"-P", "plugin:ru.capjack.tool.reflect:class=ru.capjack.tool.reflect.StubReflectB:SUPERTYPES",
				"-P", "plugin:ru.capjack.tool.reflect:class=ru.capjack.tool.reflect.StubReflectC:CONSTRUCTOR",
				"-P", "plugin:ru.capjack.tool.reflect:class=ru.capjack.tool.reflect.StubReflectD:MEMBERS"
			)
		}
	}
}