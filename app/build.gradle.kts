plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    buildFeatures {
        buildConfig = true
        viewBinding = true// Enable BuildConfig generation
    }
    namespace = "com.rohan.productapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.rohan.productapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    flavorDimensions += listOf("environment")
    productFlavors {
        create("staging") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://picsum.photos/\"")
        }
        create("production") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://fakestoreapi.com/\"")
        }
        create("live") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://fakestoreapi.com/\"")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.com.squareup.retrofit2.retrofit)
    implementation(libs.com.squareup.retrofit2.gson)
    implementation(libs.com.squareup.okhttp3.okhttp)
    implementation(libs.com.squareup.okhttp3.interceptor)
    implementation(libs.org.jetbrains.kotlinx.core)
    implementation(libs.org.jetbrains.kotlinx.android)
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.androidx.lifecycle)
    implementation(libs.com.github.bumptech.glide)
    kapt(libs.com.github.bumptech.glide.compiler)
    implementation(libs.paging.runtime)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}