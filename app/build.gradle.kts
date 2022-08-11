plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
}

android {
    compileSdk = 33
    buildToolsVersion = "33.0.0"

    defaultConfig {
        applicationId = "cz.levinzonr.router"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        kotlinCompilerExtensionVersion =  "1.3.0"
    }

    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }

}

kapt {
    arguments {
        arg("safeRoute.defaultPackageName", "cz.levinzonr.saferoute.navigation")
    }
}

ksp {
    arg("safeRoute.defaultPackageName", "cz.levinzonr.saferoute.navigation")
}


dependencies {
    val hilt_version = "2.37"
    implementation(project(":accompanist-navigation"))
    ksp(project(":processor-ksp"))
    //ksp("cz.levinzonr.safe-routing:ksp-processor:2.5.0-beta02")

    /* kapt("cz.levinzonr.safe-routing:compiler:1.0.1")
     implementation("router:core:1")*/

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.navigation:navigation-compose:${Deps.composeNavigation}")
    implementation("androidx.compose.ui:ui:${Deps.compose}")
    implementation("androidx.compose.material:material:${Deps.compose}")
    implementation("androidx.compose.ui:ui-tooling:${Deps.compose}")

    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    implementation("androidx.core:core-ktx:1.5.0")
    implementation("com.google.accompanist:accompanist-navigation-animation:${Deps.accompanist}")
    implementation("com.google.accompanist:accompanist-navigation-material:${Deps.accompanist}")



    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.activity:activity-compose:1.3.0-alpha07")
}