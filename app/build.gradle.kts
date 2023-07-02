@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp").version("1.6.10-1.0.4")
    id("kotlin-parcelize")
}

/*kotlin {
    jvmToolchain(19)
}*/

android {
    namespace = "dev.raju.consumrz"
    compileSdk = libs.versions.android.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "dev.raju.consumrz"
        minSdk = libs.versions.android.sdk.min.get().toInt()
        targetSdk = libs.versions.android.sdk.target.get().toInt()
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"//libs.versions.compose.compiler.get()
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    sourceSets {
        getByName("debug") {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
            java.srcDir("build/generated/ksp/debug/kotlin")
        }
        getByName("release") {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
            java.srcDir("build/generated/ksp/release/kotlin")
        }
    }
    /*applicationVariants.configureEach {
        println("name: $name")
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/${name}/kotlin")
                //kotlin.srcDirs("build/generated/ksp/${variant.name}/kotlin")
            }
        }
        java.sourceSets {
            getByName(name) {
                java.srcDir("build/generated/ksp/${name}/kotlin")
                //kotlin.srcDirs("build/generated/ksp/${variant.name}/kotlin")
            }
        }
    }*/

    lint {
        // If set to true (default), stops the build if errors are found.
        abortOnError = false
        // If set to true, lint only reports errors.
        ignoreWarnings = true
        // If set to true, lint also checks all dependencies as part of its analysis.
        // Recommended for projects consisting of an app with library dependencies.
        checkDependencies = true
        xmlReport = true
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(libs.core)
    implementation(libs.lifecycle.runtime)

    // compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)

    implementation(libs.bundles.compose)
    implementation(libs.compose.activity)
    implementation(libs.compose.lifecycle)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.livedata)
    implementation(libs.compose.navigation)

    implementation("androidx.compose.ui:ui:1.1.1")
    implementation("androidx.compose.material:material:1.1.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.0")

    //implementation(libs.compose.coil)
    //implementation(libs.compose.lottie)

    // dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.compose.hilt.navigation)

    // room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)

    //datastore
    implementation(libs.datastore)

    // Timber for logging
    implementation(libs.timber)

    implementation(libs.compose.destination)
    ksp(libs.compose.destination.ksp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.integration)
    androidTestImplementation(libs.espresso)

    // UI Tests
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.compose.junit)
    debugImplementation(libs.compose.test.manifest)
}