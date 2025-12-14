import org.gradle.api.internal.FeaturePreviews.Feature

enableFeaturePreview(Feature.STABLE_CONFIGURATION_CACHE.name)

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("dev.kikugie.stonecutter") version "0.7.11"
}

stonecutter {
    kotlinController = true
    centralScript = "build.gradle.kts"

    create(rootProject) {
        operator fun String.invoke(vararg loaders: String) = loaders.map { "$this-$it" to this }
        operator fun List<Pair<String, String>>.unaryPlus() = versions(this)

        +"1.20.1"("fabric", "forge")
        +"1.21.1"("neoforge")

        vcsVersion = "1.20.1-forge"
    }
}

rootProject.name = "Create Interiors"