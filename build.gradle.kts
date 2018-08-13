import ru.capjack.gradle.capjackPublish.CapjackPublishExtension
import ru.capjack.gradle.capjackPublish.CapjackPublishPlugin

plugins {
	kotlin("jvm") version "1.2.60" apply false
	id("nebula.release") version "6.3.5"
	id("ru.capjack.kotlin-sources-jar") version "0.3.0" apply false
	id("ru.capjack.capjack-publish") version "0.5.0" apply false
}

subprojects {
	group = "ru.capjack.lib"
	
	repositories {
		jcenter()
	}
	
	plugins.withType<CapjackPublishPlugin> {
		configure<CapjackPublishExtension> {
			githubRepository = "kt-logging"
		}
	}
}
