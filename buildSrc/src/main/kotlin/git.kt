import org.gradle.api.Project
import java.io.File

val Project.git
	get() = Git(rootProject.rootDir)

class Git(val repository: File) {
	fun exists() = repository.resolve(".git").isDirectory()

	// does the current git repository have uncommitted changes?
	fun isDirty() = git("status", "--porcelain").isNotBlank()

	// last commit hash
	fun hash(long: Boolean = false) =
		if(long)
			git("rev-parse", "HEAD").trim()
		else
			git("rev-parse", "--short", "HEAD").trim()

	// current branch
	fun currentBranch() = git("rev-parse", "--abbrev-ref", "HEAD").trim()

	// latest tag
	fun tag() = git("describe", "--tags", "--abbrev=0").trim()

	fun getUncommitedChanges() = git("diff", "--name-only").trim()

	private fun git(vararg args: String): String {
		val process = ProcessBuilder("git", *args)
			.directory(repository)
			.start()
		return process.inputStream.bufferedReader().readText()
	}
}