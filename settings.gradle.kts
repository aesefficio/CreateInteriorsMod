pluginManagement {
	repositories {
		mavenCentral()
		maven("https://maven.fabricmc.net")
		maven("https://maven.architectury.dev")
		maven("https://maven.minecraftforge.net")
		maven("https://maven.kikugie.dev/releases")
	}
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	id("dev.kikugie.stonecutter") version(extra.properties["stonecutter_version"].toString())
	id("dev.architectury.loom") version(extra.properties["loom_version"].toString()) apply(false)
}

stonecutter {
	kotlinController = true
	centralScript = "build.gradle.kts"
	shared {
		for(version in extra.properties["mc_versions"].toString().split("|")) {
			vers("$version-fabric", "1.$version")
			vers("$version-forge", "1.$version")
		}
	}
	create(rootProject)
}

rootProject.name = "Create Interiors"
println("----- ${rootProject.name} v${extra.properties["mod_version"]} -----")