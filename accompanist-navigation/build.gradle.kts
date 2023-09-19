plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.vanniktech.maven.publish")
}


android {
    compileSdk = 34
    namespace = "cz.levinzonr.saferoute.accompanist.navigation"
    defaultConfig {
        minSdk = 21
        targetSdk = 34
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
    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }

}

mavenPublish {
    releaseSigningEnabled = false
}


dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.navigation:navigation-compose:${Deps.composeNavigation}")
    implementation("androidx.compose.ui:ui:${Deps.compose}")
    api((project(":core")))

    implementation("com.google.accompanist:accompanist-navigation-animation:${Deps.accompanist}")
    implementation("com.google.accompanist:accompanist-navigation-material:${Deps.accompanist}")


    testImplementation("junit:junit:4.+")
    androidTestImplementation( "androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
