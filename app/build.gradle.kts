@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.gms.google-services")
}

android {
    namespace = "com.bestcoders.zaheed"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bestcoders.zaheed"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        resConfigs("ar", "en")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "ahmed_token", "1337269E-C2DF-4135-8906-85DA1A6D3754")
        }
        debug {
//            isMinifyEnabled = true
//            isShrinkResources = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
            resValue("string", "ahmed_token", "1337269E-C2DF-4135-8906-85DA1A6D3754")
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.paging.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.animation)

    // Splash screen
    implementation("androidx.core:core-splashscreen:1.0.0-beta02")

    // System Ui Controller
    implementation (libs.accompanist.systemuicontroller)


    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // ViewModel utilities for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    kapt(libs.androidx.lifecycle.compiler)
    implementation(libs.androidx.lifecycle.service)

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    // Networking
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)

    // DataStore
    implementation(libs.androidx.datastore)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.serialization.json)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Coil
    implementation(libs.coil.compose)


    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Location
    implementation(libs.play.services.location)

    // Google Map
    implementation(libs.play.services.maps)
    implementation(libs.maps.compose)
    // Optionally, you can include the Compose utils library for Clustering, etc.
    implementation(libs.maps.compose.utils)
    // Optionally, you can include the widgets library for ScaleBar, etc.
    implementation(libs.maps.compose.widgets)
    // Google Places
    implementation(libs.places)

    implementation(libs.androidx.webkit)

    implementation (libs.android.joda)


}