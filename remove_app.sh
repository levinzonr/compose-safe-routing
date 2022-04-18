echo "dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = \"router\"
include(\":annotations\")
include(\":core\")
include(\":accompanist-navigation\")
include(\":processor-kapt\")
include(\":codegen\")
include(\":processor-ksp\")
"  > settings.gradle.kts