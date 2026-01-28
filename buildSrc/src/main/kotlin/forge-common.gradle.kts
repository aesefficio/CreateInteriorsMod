import org.gradle.internal.extensions.stdlib.capitalized

plugins {
	id("java")
}

forge {
	"parchment_version".maybe {
		parchment {
			minecraftVersion = "minecraft_version"()
			mappingsVersion = it
		}
	}

	mods.maybeRegister("interiors") {
		modSourceSets.add(sourceSets.main)
	}

	runs {
		maybeRegister("client") { client() }
		maybeRegister("server") { server() }
		maybeRegister("data") {
			data()
			programArguments.addAll(listOf(
				"--mod", "interiors",
				"--existing-mod", "create",
				"--existing", file("build/generated/stonecutter/main/resources").absolutePath,
				"--all",
				"--output", file("build/generated/datagen").absolutePath
			))
			logLevel = org.slf4j.event.Level.DEBUG
		}

		configureEach {
			ideName = "${"platform"().capitalized()} ${name.capitalized()}: ${"minecraft_version"()}"
		}
	}
}

repositories {
	named("CreateMod").configure {
		content {
			includeGroupAndSubgroups("com.simibubi.create")
		}
	}
	maven(
		name = "tterrag",
		url = "https://maven.tterrag.com"
	)
}

val platformCapitalized = when ("platform"()) {
	"forge" -> "Forge"
	"neoforge" -> "NeoForge"
	else -> error("platform"())
}

dependencies {
	val modImplementation = configurations.findByName("modImplementation") ?: configurations.getByName("implementation")
	val modCompileOnly = configurations.findByName("modCompileOnly") ?: configurations.getByName("compileOnly")
	val modRuntimeOnly = configurations.findByName("modRuntimeOnly") ?: configurations.getByName("runtimeOnly")

	modImplementation("com.simibubi.create:create-${"minecraft_version"()}:${"create_version"()}:slim") {
		isTransitive = false
	}
	modImplementation(if ("minecraft_version"().startsWith("1.20")) {
		"net.createmod.ponder:Ponder-${platformCapitalized}-${"minecraft_version"()}:${"ponder_version"()}"
	} else {
		"net.createmod.ponder:ponder-${"platform"()}:${"ponder_version"()}+mc${"minecraft_version"()}"
	})
	modImplementation("com.tterrag.registrate:Registrate:${"registrate_version"()}")
	modCompileOnly("dev.engine-room.flywheel:flywheel-${"platform"()}-api-${"minecraft_version"()}:${"flywheel_version"()}")
	modRuntimeOnly("dev.engine-room.flywheel:flywheel-${"platform"()}-${"minecraft_version"()}:${"flywheel_version"()}")
}

tasks.processResources {
	filesMatching("fabric.mod.json") {
		exclude()
	}
}

operator fun String.invoke() = prop(this)
fun String.maybe(block: (String) -> Unit) = propMaybe(this)?.let(block)