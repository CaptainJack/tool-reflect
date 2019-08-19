rootProject.name = "tool-reflect"

include("tool-reflect-gradle")
project(":tool-reflect-gradle").projectDir = file("gradle-plugin")

enableFeaturePreview("GRADLE_METADATA")
