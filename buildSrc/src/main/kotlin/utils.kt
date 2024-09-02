import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven
import java.io.File
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.Deflater

fun RepositoryHandler.exclusiveMaven(url: String, vararg groups: String) {
	exclusiveContent {
		forRepository { maven(url) }
		filter {
			groups.forEach {
				includeGroup(it)
			}
		}
	}
}

fun squishJar(jar: File) {
	val contents = linkedMapOf<String, ByteArray>()
	JarFile(jar).use {
		it.entries().asIterator().forEach { entry ->
			if (!entry.isDirectory) {
				contents[entry.name] = it.getInputStream(entry).readAllBytes()
			}
		}
	}

	jar.delete()

	JarOutputStream(jar.outputStream()).use { out ->
		out.setLevel(Deflater.BEST_COMPRESSION)
		contents.forEach { var (name, data) = it
			if(name.startsWith("architectury_inject_"))
				return@forEach

			if (name.endsWith(".json") || name.endsWith(".mcmeta")) {
				data = (JsonOutput.toJson(JsonSlurper().parse(data)).toByteArray())
			}

			out.putNextEntry(JarEntry(name))
			out.write(data)
			out.closeEntry()
		}
		out.finish()
		out.close()
	}
}