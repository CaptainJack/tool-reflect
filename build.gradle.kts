plugins {
	kotlin("multiplatform") version "1.8.21"
	id("ru.capjack.publisher") version "1.1.0"
}

group = "ru.capjack.tool"

repositories {
	mavenCentral()
}

kotlin {
	jvm {
		jvmToolchain(17)
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
