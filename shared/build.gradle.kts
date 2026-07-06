import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidxRoom)
    alias(libs.plugins.ktlint)
}

val localProperties =
    Properties().apply {
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            load(localPropertiesFile.inputStream())
        }
    }

fun secret(key: String): String = localProperties.getProperty(key) ?: System.getenv(key) ?: ""

room {
    schemaDirectory("$projectDir/schemas")
}

// Android-variant-specific ktlint tasks (as opposed to the shared "androidMain"/"commonMain"
// source set tasks) also scan KSP-generated code (e.g. Room's generated DAO implementations)
// since AGP wires KSP output into the per-variant compilation. That generated code isn't ours
// to lint, and the hand-written androidMain source is already covered by the "AndroidMain"
// source set tasks above, so skip the variant-specific ones entirely.
tasks
    .matching { task ->
        task.name.startsWith("ktlintAndroidDebug") || task.name.startsWith("ktlintAndroidRelease")
    }.configureEach {
        enabled = false
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

                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)

                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor2)

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

dependencies {
    add("kspAndroid", libs.room.compiler)
}

android {
    namespace = "com.cjthdev.ganamnesis.shared"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
        buildConfigField("String", "IGDB_CLIENT_ID", "\"${secret("IGDB_CLIENT_ID")}\"")
        buildConfigField("String", "IGDB_CLIENT_SECRET", "\"${secret("IGDB_CLIENT_SECRET")}\"")
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
