import net.neoforged.moddevgradle.legacyforge.tasks.RemapJar

plugins {
	id("net.neoforged.moddev.legacyforge")
}

legacyForge {
	version = "minecraft_version"() + "-" + "forge_version"()
}

mixin {
	config("interiors.mixins.json")
}

dependencies {
	modRuntimeOnly("io.github.llamalad7:mixinextras-forge:${"mixinextras_version"()}")
	compileOnly("io.github.llamalad7:mixinextras-common:${"mixinextras_version"()}")
}

tasks.register<BetterRemapJar>("remapJar") {
	config(tasks.named<RemapJar>("reobfJar"))
	input = tasks.jar.flatMap { it.archiveFile }

	manifest.attributes(
		"MixinConfigs" to "interiors.mixins.json",
	)
}

tasks.named<RemapJar>("reobfJar") {
	enabled = false
	finalizedBy(tasks.named("remapJar"))
}

apply(plugin = "forge-common")

operator fun String.invoke() = prop(this)