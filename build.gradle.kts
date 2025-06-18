// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
}
configurations.all {
    resolutionStrategy {
        // This forces Gradle to use JavaPoet 1.13.0 for all transitive dependencies
        // If 1.13.0 doesn't work, try other recent versions from Maven Central.
        force("com.squareup:javapoet:1.13.0")
    }
}