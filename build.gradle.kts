plugins {
	kotlin("jvm") version "1.2.60" apply false
	id("io.freefair.sources-jar") version "2.5.11" apply false
	id("ru.capjack.capjack-publish") version "0.7.0" apply false
	id("com.gradle.plugin-publish") version "0.10.0" apply false
	id("nebula.release") version "6.3.5"
}

subprojects {
	group = "ru.capjack"
	
	repositories {
		jcenter()
	}
}