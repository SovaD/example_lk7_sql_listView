plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.example_lk4_arrayadapter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.example_lk4_arrayadapter"
        minSdk = 28
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.caverock:androidsvg:1.4")
    // use annotationProcessor for non kotlin projects
    // annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    //kapt("com.github.bumptech.glide:compiler:4.16.0")
}