import org.gradle.kotlin.dsl.support.classPathBytesRepositoryFor

pluginManagement {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }

}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

rootProject.name = "Lab5"
include(":app")
