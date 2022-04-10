dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = "router"
include(":annotations")
include(":compiler")
include(":core")
include(":accompanist-navigation")
include(":app")
include(":codegen")
