@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "19.2-fabric" /* [SC] DO NOT EDIT */

stonecutter registerChiseled tasks.register("chiseledAssemble", stonecutter.chiseled) {
    group = "project"
    ofTask("assemble")
}

stonecutter.configureEach {
	project.ext["loom.platform"] = ModLoader.detect(project).name.lowercase()
}

project(stonecutter.current.project).file("build.properties").apply {
	if(!exists()) {
		return@apply
	}

	copyTo(rootProject.file("build.properties"), overwrite = true)
}

println("Stonecutter active for ${stonecutter.current.project}")
