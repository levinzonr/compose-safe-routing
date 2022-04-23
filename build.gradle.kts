// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val compose_version by extra("1.1.0")
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.17.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}


plugins {
    id("com.diffplug.spotless") version "6.1.0"
}

subprojects {
    apply(plugin="com.diffplug.spotless")

    spotless {
        kotlin {
            target("**/*.kt")
            ktlint("0.44.0")
        }
    }

}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}