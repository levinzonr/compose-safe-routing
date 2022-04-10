plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.4.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")

}