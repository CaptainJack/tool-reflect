plugins {
	kotlin("multiplatform") version "1.4.10"
	id("nebula.release") version "15.2.0"
	id("ru.capjack.bintray") version "1.0.0"
}

group = "ru.capjack.tool"
repositories {
	jcenter()
}

kotlin {
	jvm {
		compilations.all { kotlinOptions.jvmTarget = "1.8" }
	}
	
	sourceSets {
		get("commonMain").dependencies {
			implementation(kotlin("reflect"))
		}
		get("commonTest").dependencies {
			implementation(kotlin("test-common"))
			implementation(kotlin("test-annotations-common"))
		}
		
		get("jvmMain").dependencies {
		}
		get("jvmTest").dependencies {
			implementation(kotlin("test-junit"))
		}
	}
}
