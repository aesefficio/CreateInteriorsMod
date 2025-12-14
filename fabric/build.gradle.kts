architectury.fabric()

loom {
	val common = project(":common")
	accessWidenerPath = common.loom.accessWidenerPath

	runs.configureEach {
		vmArg("-Dmixin.debug.export=true")
		vmArg("-Dmixin.env.remapRefMap=true")
		vmArg("-Dmixin.env.refMapRemappingFile=${projectDir}/build/createSrgToMcp/output.srg")
	}


	runs {
		create("datagen") {
			client()

			name = "Minecraft Data"
			vmArg("-Dfabric-api.datagen")
			vmArg("-Dfabric-api.datagen.output-dir=${common.file("src/generated/resources")}")
			vmArg("-Dfabric-api.datagen.modid=interiors")
			vmArg("-Dporting_lib.datagen.existing_resources=${common.file("src/main/resources")}")

			environmentVariable("DATAGEN", "TRUE")
		}
	}
}

dependencies {
	modImplementation("net.fabricmc:fabric-loader:${"fabric_loader_version"()}")
	modImplementation("net.fabricmc.fabric-api:fabric-api:${"fabric_api_version"()}+${"minecraft_version"()}")

	// Create - dependencies are added transitively
	modImplementation("com.simibubi.create:create-fabric:${"create_fabric_version"()}")

	// Development QOL
	modLocalRuntime("maven.modrinth:lazydfu:${"lazydfu_version"()}")
	modLocalRuntime("com.terraformersmc:modmenu:${"modmenu_version"()}")
}

operator fun String.invoke() = rootProject.ext[this] as? String ?: error("No property \"$this\"")