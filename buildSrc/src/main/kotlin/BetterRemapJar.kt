import net.fabricmc.loom.util.TinyRemapperHelper
import net.fabricmc.loom.util.TinyRemapperLoggerAdapter
import net.fabricmc.tinyremapper.TinyRemapper
import net.fabricmc.tinyremapper.extension.mixin.MixinExtension

import net.neoforged.moddevgradle.legacyforge.tasks.RemapJar
import net.neoforged.moddevgradle.legacyforge.tasks.RemapOperation

import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.ArchiveOperations

import javax.inject.Inject
import kotlin.io.path.exists

@CacheableTask
abstract class BetterRemapJar : Jar() {
	@get:InputFile
	@get:PathSensitive(PathSensitivity.NONE)
	abstract val input: RegularFileProperty

	@get:Nested
	abstract val remapOperation: RemapOperation

	@get:Optional
	@get:InputFiles
	@get:PathSensitive(PathSensitivity.NONE)
	abstract val libraries: ConfigurableFileCollection

	@get:Inject
	abstract val archiveOps: ArchiveOperations

	init {
		duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	}

	fun config(task: TaskProvider<RemapJar>) {
		this.libraries.from(task.map { it.libraries })
		this.remapOperation.mappings.from(task.map { it.remapOperation.mappings })
		this.remapOperation.toolType.set(task.flatMap { it.remapOperation.toolType })
	}

	@TaskAction
	fun execute() {
		val mappings = remapOperation.mappings.files.single()
		val input = input.get().asFile
		val mapper = TinyRemapper.newRemapper(TinyRemapperLoggerAdapter.INSTANCE)
			.withMappings(TinyRemapperHelper.create(
				mappings.toPath(),
				// these *are* important
				"source", "target",
				true
			))
			.extension(MixinExtension())
			.ignoreFieldDesc(true)
			.build()

		mapper.readClassPath(*libraries.map { it.toPath() }.filter { it.exists() }.toTypedArray())
		mapper.readInputs(input.toPath())

		val output = temporaryDir.resolve("output")
		output.mkdirs()

		val names = mutableSetOf<String>()

		mapper.apply { name, bytes ->
			names.add("$name.class")
			val outputFile = output.resolve("$name.class")
			outputFile.parentFile.mkdirs()
			outputFile.writeBytes(bytes)
		}

		mapper.finish()

		from(output)
		from(archiveOps.zipTree(input)) {
			exclude { it.name in names }
		}

		super.copy()
	}
}