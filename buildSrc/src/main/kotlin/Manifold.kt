import org.gradle.api.Project
import java.util.Properties

object Manifold {
	fun generateProperties(current: String,
						   loader: ModLoader,
						   compilerArgs: MutableList<String>? = null,
						   mcVersions: List<String>
	): Properties {
		if(!mcVersions.contains(current)) {
			throw IllegalArgumentException("Current version must be one of the provided versions")
		}

		val properties = mutableListOf<String>()
		val sorted = mcVersions.sorted()

		val currentIndex = sorted.indexOf(current)

		for(i in sorted.indices) {
			val mcVer = sorted[i].replace(".", "_")

			if(currentIndex < i) {
				properties.add("PRE_MC_${mcVer}")
			}
			if(currentIndex <= i) {
				properties.add("PRE_CURRENT_MC_${mcVer}")
			}
			if(currentIndex > i) {
				properties.add("POST_MC_${mcVer}")
			}
			if(currentIndex >= i) {
				properties.add("POST_CURRENT_MC_${mcVer}")
			}
			if(currentIndex == i) {
				properties.add("MC_${mcVer}")
			}
		}

		return Properties().apply {
			properties.forEach { prop ->
				setProperty(prop, "")
				compilerArgs?.add("-A$prop")
			}
			setProperty(loader.name, "")
		}
	}
}