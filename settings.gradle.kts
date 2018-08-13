include("gradle-ktjs-reflect")

enableFeaturePreview("STABLE_PUBLISHING")

pluginManagement.resolutionStrategy.eachPlugin {
	if (requested.id.id == "kotlin2js") {
		useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
	}
}