plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvmToolchain(21)
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "21"
            }
        }
    }
    
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(libs.google.id)
                implementation(libs.androidx.credentials)
                implementation(libs.ktor.client.okhttp)
                implementation(compose.uiTooling)
                implementation(compose.components.uiToolingPreview)
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.koin.compose)

                implementation(libs.firebase.auth)

                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.androidx.navigation.compose)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.logging)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)
            }
        }
    }
}

android {
    namespace = "com.cjthdev.ganamnesis.shared"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
