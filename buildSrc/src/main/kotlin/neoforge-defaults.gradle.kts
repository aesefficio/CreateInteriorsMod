plugins {
	id("net.neoforged.moddev")
}

neoForge {
	version = "minecraft_version"().removePrefix("1.") + "." + "forge_version"()
}

apply(plugin = "forge-common")

tasks.processResources {
	filesMatching("META-INF/mods.toml") {
		name = "neoforge.mods.toml"
	}
}

operator fun String.invoke() = prop(this)