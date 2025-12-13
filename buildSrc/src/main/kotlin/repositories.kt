import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.artifacts.repositories.RepositoryContentDescriptor
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.maven(
	name: String? = null,
	url: String,
	content: RepositoryContentDescriptor.() -> Unit = {}
) = maven(url) {
	if (name != null)
		this.name = name
	content(content)
}

fun RepositoryHandler.exclusiveMaven(
	name: String? = null,
	url: String,
	content: InclusiveRepositoryContentDescriptor.() -> Unit = {}
) = exclusiveContent {
	forRepository {
		maven(name, url)
	}
	filter(content)
}