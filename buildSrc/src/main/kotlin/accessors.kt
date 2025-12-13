// gradle bad

import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

val Project.manifold get() = the<xyz.wagyourtail.manifold.plugin.ManifoldExtension>()
fun Project.manifold(block: xyz.wagyourtail.manifold.plugin.ManifoldExtension.() -> Unit) {
	manifold.apply(block)
}

val Project.loom get() = the<net.fabricmc.loom.api.LoomGradleExtensionAPI>()
fun Project.loom(block: net.fabricmc.loom.api.LoomGradleExtensionAPI.() -> Unit) {
	loom.apply(block)
}

val Project.forge get() = the<net.neoforged.moddevgradle.dsl.ModDevExtension>()
fun Project.forge(block: net.neoforged.moddevgradle.dsl.ModDevExtension.() -> Unit) {
	forge.apply(block)
}

val Project.neoForge get() = the<net.neoforged.moddevgradle.dsl.NeoForgeExtension>()
fun Project.neoForge(block: net.neoforged.moddevgradle.dsl.NeoForgeExtension.() -> Unit) {
	neoForge.apply(block)
}

val Project.legacyForge get() = the<net.neoforged.moddevgradle.legacyforge.dsl.LegacyForgeExtension>()
fun Project.legacyForge(block: net.neoforged.moddevgradle.legacyforge.dsl.LegacyForgeExtension.() -> Unit) {
	legacyForge.apply(block)
}

val Project.mixin get() = the<net.neoforged.moddevgradle.legacyforge.dsl.MixinExtension>()
fun Project.mixin(block: net.neoforged.moddevgradle.legacyforge.dsl.MixinExtension.() -> Unit) {
	mixin.apply(block)
}

val Project.sourceSets get() = the<org.gradle.api.tasks.SourceSetContainer>()