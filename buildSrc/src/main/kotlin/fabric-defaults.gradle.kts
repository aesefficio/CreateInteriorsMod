import net.fabricmc.loom.util.gradle.SourceSetReference

plugins {
	id("fabric-loom")
}

repositories {
	exclusiveMaven(
		name = "ParchmentMC",
		url = "https://maven.parchmentmc.org"
	) {
		includeGroupAndSubgroups("org.parchmentmc")
	}

	exclusiveMaven(
		name = "TerraformersMC",
		url = "https://maven.terraformersmc.com"
	) {
		includeGroupAndSubgroups("com.terraformersmc")
	}

	exclusiveMaven(
		name = "Fuzs Mod Resources",
		url = "https://raw.githubusercontent.com/Fuzss/modresources/main/maven"
	) {
		includeGroupAndSubgroups("fuzs")
	}

	maven("Cafeteria", "https://maven.cafeteria.dev/releases")
	maven("JamiesWhiteShirt", "https://maven.jamieswhiteshirt.com/libs-release") {
		includeGroupAndSubgroups("com.jamieswhiteshirt")
	}
}

dependencies {
	minecraft("com.mojang:minecraft:${"minecraft_version"()}")
	mappings(loom.layered {
		officialMojangMappings()
		"parchment_version".maybe {
			parchment("org.parchmentmc.data:parchment-${"minecraft_version"()}:${it}@zip")
		}
	})

	modImplementation("net.fabricmc:fabric-loader:${"fabric_version"()}")
	modImplementation("net.fabricmc.fabric-api:fabric-api:${"fabric_api_version"()}+${"minecraft_version"()}")

	modImplementation("com.simibubi.create:create-fabric:${"create_version"()}-mc${"minecraft_version"()}")
	modImplementation("com.terraformersmc:modmenu:${"modmenu_version"()}")
	modLocalRuntime("net.fabricmc.fabric-api:fabric-api-deprecated:${"fabric_api_version"()}+${"minecraft_version"()}")
}

gradle.taskGraph.whenReady {
	allTasks.filter { it.name == "net.fabricmc.devlaunchinjector.Main.main()" }.forEach {
		it.notCompatibleWithConfigurationCache("loom weird?")
	}
}

loom {
	mods {
		maybeRegister("interiors") {
			modSourceSets.add(sourceSets.main.map { SourceSetReference(it, project) })
		}
	}

	runs {
		maybeRegister("client") {
			client()
			name("Fabric Client: " + "minecraft_version"())
		}

		maybeRegister("server") {
			server()
			name("Fabric Server: " + "minecraft_version"())
		}

		maybeRegister("data") {
			client()

			name("Fabric Data: " + "minecraft_version"())
			property("fabric-api.datagen")
			property("fabric-api.datagen.output-dir", file("build/generated/datagen").absolutePath)
			property("fabric-api.datagen.modid", "interiors")
			property("porting_lib.datagen.existing_resources", file("build/generated/stonecutter/main/resources").absolutePath)
			property("porting_lib.datagen.existing-mod", "create")

			environmentVariable("DATAGEN", "TRUE")
		}

		configureEach {
			ideConfigGenerated(true)
			appendProjectPathToConfigName = false
		}
	}

	mixin {
		useLegacyMixinAp = false
	}
}

tasks.processResources {
	filesMatching("META-INF/*.toml") {
		exclude()
	}
}

operator fun String.invoke() = prop(this)
fun String.maybe(block: (String) -> Unit) = propMaybe(this)?.let(block)