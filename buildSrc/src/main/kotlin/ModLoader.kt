import org.gradle.api.Project

enum class ModLoader {
	FABRIC, FORGE;

	companion object {
		fun detect(project: Project): ModLoader {
			val name = project.name
			return when {
				name.endsWith("-fabric") -> FABRIC
				name.endsWith("-forge") -> FORGE
				else -> error("Mod loader not detected for project $name")
			}
		}
	}
}