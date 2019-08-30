plugins {
	kotlin("multiplatform") version "1.3.50"
	id("nebula.release") version "11.1.0"
	id("ru.capjack.bintray") version "1.0.0"
}

allprojects {
	group = "ru.capjack.tool"
	repositories {
		jcenter()
	}
}

kotlin {
	jvm {
		compilations.all { kotlinOptions.jvmTarget = "1.8" }
	}
	js {
		browser()
		compilations["main"].kotlinOptions {
			sourceMap = true
			sourceMapEmbedSources = "always"
		}
		compilations["test"].compileKotlinTask.apply {
			evaluationDependsOn(":tool-reflect-gradle")
			val jar = project(":tool-reflect-gradle").tasks.getByName<Jar>("jar")
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
	
	sourceSets {
		get("commonMain").dependencies {
			implementation(kotlin("stdlib-common"))
			implementation(kotlin("reflect"))
		}
		get("commonTest").dependencies {
			implementation(kotlin("test-common"))
			implementation(kotlin("test-annotations-common"))
		}
		
		get("jvmMain").dependencies {
			implementation(kotlin("stdlib-jdk8"))
		}
		get("jvmTest").dependencies {
			implementation(kotlin("test-junit"))
		}
		
		get("jsMain").dependencies {
			implementation(kotlin("stdlib-js"))
		}
		get("jsTest").dependencies {
			implementation(kotlin("test-js"))
		}
	}
}
