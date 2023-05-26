plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "dev.raju.data"
    compileSdk = libs.versions.android.sdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.sdk.min.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(projects.domain)
    implementation(libs.core)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    //datastore
    implementation(libs.datastore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.integration)
    androidTestImplementation(libs.espresso)
}