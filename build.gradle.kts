plugins {
	kotlin("jvm") version "1.2.61" apply false
	id("io.freefair.sources-jar") version "2.7.3" apply false
	id("ru.capjack.capjack-bintray") version "0.8.0" apply false
	id("nebula.release") version "8.0.3"
}

subprojects {
	group = "ru.capjack.lib.kt.reflect"
	
	repositories {
		jcenter()
	}
}