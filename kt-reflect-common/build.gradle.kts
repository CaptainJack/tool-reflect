plugins {
	kotlin("platform.common")
	id("io.freefair.sources-jar")
	id("ru.capjack.capjack-bintray")
}

dependencies {
	implementation(kotlin("stdlib-common"))
	
	testImplementation(kotlin("test-common"))
	testImplementation(kotlin("test-annotations-common"))
}