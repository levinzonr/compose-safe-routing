plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.vanniktech.maven.publish")
}


android {
    compileSdk = 30

    defaultConfig {
        minSdk = 21
        targetSdk = 30
        version  = 1

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
        kotlinCompilerVersion = "1.4.32"
    }



}

mavenPublish {
    releaseSigningEnabled = false
}


dependencies {

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0-alpha03")
    implementation("androidx.navigation:navigation-compose:${Deps.composeNavigation}")
    implementation("androidx.compose.ui:ui:${Deps.compose}")
    implementation("androidx.compose.material:material:${Deps.compose}")
    implementation("androidx.compose.ui:ui-tooling:${Deps.compose}")
    api(project(":annotations"))

    testImplementation("junit:junit:4.+")
    androidTestImplementation( "androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}