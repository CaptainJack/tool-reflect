plugins {
	kotlin("multiplatform") version "1.5.0"
	id("ru.capjack.publisher") version "0.1.0"
}

group = "ru.capjack.tool"

repositories {
	mavenCentral()
}

kotlin {
	jvm {
		compilations.all { kotlinOptions.jvmTarget = "11" }
	}
	
	sourceSets {
		get("commonMain").dependencies {
			api(kotlin("reflect"))
		}
		get("commonTest").dependencies {
			implementation(kotlin("test"))
		}
	}
}
