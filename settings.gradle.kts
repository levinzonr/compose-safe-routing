dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        jcenter() // Warning: this repository is going to shut down soon
    }
}

pluginManagement {
    val kotlinVersion = "1.6.20"
    val kspVersion: String = "1.6.20-1.0.5"
    plugins {
        id("com.google.devtools.ksp") version kspVersion
        kotlin("jvm") version kotlinVersion
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}



rootProject.name = "router"
include(":annotations")
include(":processor-kapt")
include(":core")
include(":accompanist-navigation")
include(":app")
include(":codegen")
include(":processor-ksp")
