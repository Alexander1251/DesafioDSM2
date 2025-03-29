plugins {
    // Plugin oficial de Android para aplicaciones
    id("com.android.application") version "8.1.0"

    // Kotlin Android
    kotlin("android") version "1.8.21"

    // Plugin de Google Services (Firebase)
    id("com.google.gms.google-services") version "4.3.15"
}

android {
    namespace = "com.example.meerkat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.meerkat"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    // Elimina o comenta el bloque de Compose
    // buildFeatures {
    //     compose = true
    // }
    // composeOptions {
    //     kotlinCompilerExtensionVersion = "1.4.0"
    // }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    implementation(platform("com.google.firebase:firebase-bom:32.1.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
