@file:Suppress("NOTHING_TO_INLINE")

import org.gradle.api.Project
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.tasks.TaskProvider
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.named

/**
 * note that this can't be used in buildscripts and must be copied:
 * `operator fun String.invoke() = prop(this)`
 * because you can't enable context parameters in buildscripts :(
 */
context(p: Project)
inline operator fun String.invoke() = p.prop(this)

// for when you're not in a Project context
fun Project.propMaybe(name: String): String? {
	var current: Project? = this
	while (current != null) {
		val value = current.properties[name] as? String
		if (value != null) return value
		current = current.parent
	}
	return null
}

fun Project.prop(name: String): String {
	return propMaybe(name) ?: error("Property $name not found for project ${project.name}")
}

context(p: Project)
inline fun String.maybe(block: (String) -> Unit) = p.propMaybe(this)?.let(block)

val Project.jarOutputTask: TaskProvider<out Jar> get() = tasks.named<Jar>(
	if ("platform"() == "neoforge") "jar" else "remapJar"
)

// gradle doesn't have a lazy version of this? idk why
inline fun <T : Any> NamedDomainObjectContainer<T>.maybeRegister(name: String, noinline config: T.() -> Unit = {}): NamedDomainObjectProvider<T> {
	val provider = if (this.names.contains(name)) this.named(name) else this.register(name)
	provider.configure(config)
	return provider
}