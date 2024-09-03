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

		compilerArgs?.addAll(listOf(
			"-AMC=${current.replace(".", "0")}",
			"-A${loader.name}"
		))

		return Properties().apply {
			setProperty("MC", current)
			setProperty(loader.name, "")
		}
	}
}