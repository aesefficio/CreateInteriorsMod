architectury.forge()

loom {
	runs.configureEach {
		vmArg("-Dmixin.debug.export=true")
		vmArg("-Dmixin.env.remapRefMap=true")
		vmArg("-Dmixin.env.refMapRemappingFile=${projectDir}/build/createSrgToMcp/output.srg")
	}

	forge {
		mixinConfig("interiors-common.mixins.json")
		mixinConfig("interiors.mixins.json")
	}
}

dependencies {
	forge("net.minecraftforge:forge:${"minecraft_version"()}-${"forge_version"()}")

	// Create and its dependencies
	modImplementation("com.simibubi.create:create-${"minecraft_version"()}:${"create_forge_version"()}:slim") { isTransitive = false }
	modImplementation("net.createmod.ponder:Ponder-Forge-${"minecraft_version"()}:${"ponder_version"()}")
	modImplementation("com.tterrag.registrate:Registrate:${"registrate_version"()}")
	modImplementation("dev.engine-room.flywheel:flywheel-forge-api-${"minecraft_version"()}:${"flywheel_version"()}")
	modImplementation("io.github.llamalad7:mixinextras-forge:${"mixin_extras_version"()}")
	modRuntimeOnly("dev.engine-room.flywheel:flywheel-forge-${"minecraft_version"()}:${"flywheel_version"()}")
}

operator fun String.invoke() = rootProject.ext[this] as? String ?: error("No property \"$this\"")