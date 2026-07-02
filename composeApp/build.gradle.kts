plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.googleServices)
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
                implementation(project(":shared"))
                implementation("androidx.activity:activity-compose:1.9.1")
                implementation("io.insert-koin:koin-android:3.5.6")
            }
        }
    }
}

android {
    namespace = "com.cjthdev.ganamnesis"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.cjthdev.ganamnesis"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
