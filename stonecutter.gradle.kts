import org.gradle.kotlin.dsl.stonecutter

plugins {
	id("base")
	id("dev.kikugie.stonecutter")
	id("xyz.wagyourtail.manifold")
}

stonecutter active "1.20.1-forge"

rootProject.group = "com.aesefficio"
rootProject.base.archivesName = "interiors"
rootProject.version = prop("mod_version")

manifold {
	version = prop("manifold_version")
	pluginArgs.add("--no-bootstrap")

	subprojectPreprocessor {
		for (p in stonecutter.versions.map { project(it.project) }) {
			val stonecutter = p.the<dev.kikugie.stonecutter.build.StonecutterBuildExtension>()
			subproject(p) {
				property("MC", stonecutter.current.version.removePrefix("1."))
				stonecutter.constants.forEach {
					if (it.value) { property(it.key) }
				}
			}
		}

		if (stonecutter.current != null) {
			ideActiveSubproject = stonecutter.current!!.project
		}
	}
}
