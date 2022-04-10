plugins {
    kotlin("jvm")
}


dependencies {
    implementation(project(":codegen"))
    implementation("com.google.devtools.ksp:symbol-processing-api:1.6.10-1.0.3")
}