plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.project.shopz"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.project.shopz"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

configurations {
    all {
        exclude(group = "org.xmlpull", module = "xmlpull")
        exclude(group = "xmlpull", module = "xmlpull")
        exclude(group = "xpp3", module = "xpp3")
        resolutionStrategy {
            force("org.xmlpull:xmlpull:1.1.3.1")
            force("xpp3:xpp3:1.1.4c")
        }
    }
}

dependencies {
    constraints {
        implementation("org.xmlpull:xmlpull:1.1.3.1")
        implementation("xpp3:xpp3:1.1.4c")
    }

    //shimmer effect
    implementation("com.google.accompanist:accompanist-placeholder-material:0.31.0-alpha")

    

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.safe.args.generator)
    implementation(libs.androidx.compose.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ... other Compose dependencies ...

    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    // retrofit 
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")


    implementation("io.coil-kt.coil3:coil-compose:3.0.4")

    implementation("io.coil-kt:coil-compose:2.5.0")

    implementation(libs.hilt.compose.navigation)

    //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    val nav_version = "2.8.5"

    implementation("androidx.navigation:navigation-compose:$nav_version")
}

kapt {
    correctErrorTypes = true
}

configurations.all {
    resolutionStrategy {
        force("org.xmlpull:xmlpull:1.1.3.1")
    }
}