@file:Suppress("UnstableApiUsage")

plugins {
	id("java")
	id("idea")
	id("dev.architectury.loom")
}

val modLoader = ModLoader.detect(project)
project.ext["minecraft_version"] = stonecutter.current.version

val buildNumber = System.getenv("GITHUB_RUN_NUMBER")?.toInt()
val gitHash = "\"${git.hash() + (if (git.isDirty()) "-modified" else "")}\""

base.archivesName = "archives_base_name"()
version = "${"mod_version"()}+${modLoader.name.lowercase()}-mc${"minecraft_version"()}${buildNumber?.let { "-build.$it" } ?: ""}"
group = "maven_group"()

idea.module {
	isDownloadSources = true
}

tasks.register("generateProperties") {
	group = "build"

	doLast {
		val props = Manifold.generateProperties(
			current = stonecutter.current.version.substring(2),
			loader = modLoader,
			mcVersions = "mc_versions"().split("|")
		)

		file("build.properties").apply {
			if(!exists()) {
				createNewFile()
			}

			outputStream().use { stream ->
				props.store(stream, "DO NOT EDIT\nGenerated properties for the build")
			}
		}
	}
}

repositories {
	exclusiveMaven("https://maven.parchmentmc.org", "org.parchmentmc.data")
	exclusiveMaven("https://maven.quiltmc.org/repository/release", "org.quiltmc")
	exclusiveMaven("https://api.modrinth.com/maven", "maven.modrinth")
	exclusiveMaven("https://cursemaven.com", "curse.maven")
	maven("https://maven.theillusivec4.top/")
	maven("https://maven.tterrag.com/") {
		content {
			includeGroup("com.simibubi.create")
			includeGroup("com.tterrag.registrate")
			includeGroup("com.jozufozu.flywheel")
		}
	}
	exclusiveMaven("https://maven.jamieswhiteshirt.com/libs-release", "com.jameswhiteshirt.reach-entity-attributes")
	maven("https://maven.terraformersmc.com/releases/")
	maven("https://mvn.devos.one/snapshots/")
	maven("https://maven.cafeteria.dev/releases")
	maven("https://maven.jamieswhiteshirt.com/libs-release")
	maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
	maven("https://jitpack.io") {
		content {
			includeGroupAndSubgroups("com.github")
			includeGroupAndSubgroups("io.github")
		}
	}

	if(modLoader == ModLoader.FORGE) {
		maven("https://files.minecraftforge.net/maven")
	}
	if(modLoader == ModLoader.FABRIC) {
		maven("https://maven.fabricmc.net")
	}
}

java {
	withSourcesJar()
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

tasks.compileJava {
	options.encoding = "UTF-8"
	options.compilerArgs.add("-Xplugin:Manifold")
	Manifold.generateProperties(
		current = stonecutter.current.version.substring(2),
		loader = modLoader,
		compilerArgs = options.compilerArgs,
		mcVersions = "mc_versions"().split("|")
	)

	doFirst {
		rootProject.file("build.properties").delete() // don't let it mess with the preprocessor
	}

	finalizedBy("generateProperties")
}

sourceSets.main {
	resources.srcDir("src/generated/resources")
}

configurations.configureEach {
	resolutionStrategy {
		force("net.fabricmc:fabric-loader:${"fabric_version"()}")
	}
}

tasks.remapJar {
	injectAccessWidener = true
	archiveClassifier = null
	destinationDirectory = rootProject.layout.buildDirectory.dir("libs")

	doLast {
		squishJar(archiveFile.get().asFile)
	}
}

tasks.jar {
	manifest {
		attributes(mapOf(
			"Git-Hash" to git.hash(),
			"Git-Branch" to git.currentBranch(),
		))
	}
}

loom {
	silentMojangMappingsLicense()

	runs.configureEach {
		vmArg("-Dmixin.debug.export=true")
		vmArg("-Dmixin.env.remapRefMap=true")
		vmArg("-Dmixin.env.refMapRemappingFile=${projectDir}/build/createSrgToMcp/output.srg")
	}

	if(modLoader == ModLoader.FABRIC) {
		runs.create("datagen") {
			client()

			name = "Minecraft Data"
			vmArg("-Dfabric-api.datagen")
			vmArg("-Dfabric-api.datagen.output-dir=${rootProject.file("src/generated-${"minecraft_version"}/resources")}")
			vmArg("-Dfabric-api.datagen.modid=interiors")
			vmArg("-Dporting_lib.datagen.existing_resources=${rootProject.file("src/main/resources")}")

			environmentVariable("DATAGEN", "TRUE")
		}
	}

	if(modLoader == ModLoader.FORGE) {
		forge {
			mixinConfig("interiors.mixins.json")
		}
	}
}

dependencies {
	minecraft("com.mojang:minecraft:${"minecraft_version"()}")
	mappings(loom.layered {
		mappings("org.quiltmc:quilt-mappings:${"minecraft_version"()}+build.${"qm_version"()}:intermediary-v2")
		officialMojangMappings { nameSyntheticMembers = false }
		parchment("org.parchmentmc.data:parchment-${"minecraft_version"()}:${"parchment_version"()}@zip")
	})

	if(modLoader == ModLoader.FORGE) {
		"forge"("net.minecraftforge:forge:${"minecraft_version"()}-${"forge_version"()}")

		modImplementation("com.simibubi.create:create-${"minecraft_version"()}:${"create_version"()}:slim") { isTransitive = false }
		val registrateVersion = if(project.hasProperty("registrate_mc_version")) {
			"MC${"registrate_mc_version"()}-${"registrate_version"()}"
		} else {
			"MC${"minecraft_version"()}-${"registrate_version"()}"
		}
		modImplementation("com.tterrag.registrate:Registrate:${registrateVersion}")
		modImplementation("com.jozufozu.flywheel:flywheel-forge-${"minecraft_version"()}:${"flywheel_version"()}")

		compileOnly("io.github.llamalad7:mixinextras-common:${"mixin_extras_version"()}")
		annotationProcessor(implementation(include("io.github.llamalad7:mixinextras-forge:${"mixin_extras_version"()}")!!)!!)
	}

	if(modLoader == ModLoader.FABRIC) {
		modImplementation("net.fabricmc:fabric-loader:${"fabric_version"()}")
		modImplementation("net.fabricmc.fabric-api:fabric-api:${"fabric_api_version"()}+${"minecraft_version"()}")

		modImplementation("com.simibubi.create:create-fabric-${"minecraft_version"()}:${"create_version"()}+mc${"minecraft_version"()}") {
			exclude(group = "com.github.llamalad7.mixinextras", module = "mixinextras")
			exclude(group = "com.github.LlamaLad7", module = "MixinExtras")
		}

		modLocalRuntime("com.terraformersmc:modmenu:${"modmenu_version"()}")
	}

	annotationProcessor("systems.manifold:manifold-preprocessor:${"manifold_version"()}")
}

tasks.processResources {
	val properties = mapOf(
		"version" to project.version,
		"minecraft_version" to "minecraft_version"(),
		"fabric_api_version" to "fabric_api_version"(),
		"fabric_version" to "fabric_version"(),
		"forge_version" to "forge_version"().substringBefore("."), // only specify major version of forge
		"create_version" to "create_version"().let {
			when (modLoader) {
				ModLoader.FORGE -> it.substringBefore("-") // cut off build number
				ModLoader.FABRIC -> it.substringBefore("+") // Trim +mc1.XX.X from version string
				else -> it
			}
		},
	)

	inputs.properties(properties)

	filesMatching(listOf("fabric.mod.json", "META-INF/mods.toml")) {
		expand(properties)
	}

	if(modLoader == ModLoader.FORGE) {
		exclude("fabric.mod.json")
	} else if(modLoader == ModLoader.FABRIC) {
		exclude("META-INF/mods.toml", "pack.mcmeta")
	}
}

operator fun String.invoke() = project.properties[this] as? String ?: error("Property $this not found")