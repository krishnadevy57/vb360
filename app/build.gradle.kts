plugins {
    alias(libs.plugins.android.application)
    id ("com.google.gms.google-services")
}

android {
    namespace = "com.mind2web.vb360"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mind2web.vb360"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation (libs.audioswitch)
    implementation (libs.lottie)
    implementation (libs.libphonenumber)
    implementation (libs.richeditor.android)
    implementation (libs.ccp)
    implementation(libs.activity)
    implementation(libs.multidex)
    implementation (libs.jjwt.api) // For libs.jjwt.api
    implementation (libs.jjwt.impl) // For libs.jjwt.impl
    implementation (libs.jjwt.jackson) // For libs.jjwt.jackson
    implementation (libs.voice.android) // For libs.voice.android
    implementation (libs.lifecycle.process) // For libs.lifecycle.process
    implementation (platform(libs.google.firebase.bom)) // For libs.firebase.bom
    implementation (libs.firebase.analytics) // For libs.firebase.analytics
    implementation (libs.google.firebase.messaging) // For libs.firebase.messaging
    implementation (libs.volley) // For libs.volley
    implementation (libs.gson) // For libs.gson
    implementation (libs.okhttp) // For libs.okhttp
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

