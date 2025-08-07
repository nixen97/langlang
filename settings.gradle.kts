plugins {
    id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
}

rootProject.name = "langlang"

compiler {
    subproject("lexer")
}

// --- Project hierarchy stuff ---
fun compiler(moduleConfiguration: ProjectScope.() -> Unit) =
    ProjectScope("compiler").moduleConfiguration()

class ProjectScope(
    private val basePath: String
) {
    fun subproject(projectName: String) {
        include(projectName)
        project(":$projectName").projectDir = file("$basePath/$projectName")
    }
}
