plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id ("kotlin-parcelize")
    id("com.google.devtools.ksp")
    kotlin("kapt")
}

android {
    namespace = "com.example.planet"
    compileSdk = 34

    testOptions { packagingOptions { jniLibs { useLegacyPackaging = true } } }

    defaultConfig {
        applicationId = "com.example.planet"
        minSdk = 29
        targetSdk = 34
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    val navVersion = "2.7.7"
    val retrofit = "2.9.0"
    val loggingInterceptor = "4.5.0"
    val hiltAndroid = "2.48"
    val coroutines = "1.7.1"
    val hiltNavigationCompose = "1.2.0"
    val mockAndroid = "1.13.8"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Integration Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Navigation
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("androidx.navigation:navigation-testing:2.7.7")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:$retrofit")
    implementation ("com.squareup.okhttp3:logging-interceptor:$loggingInterceptor")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit")
    implementation ("com.squareup.retrofit2:adapter-rxjava3:$retrofit")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:$hiltAndroid")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationCompose")
    kapt("com.google.dagger:hilt-android-compiler:$hiltAndroid")

    //Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")

    //Room
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    //Unit test cases
    androidTestImplementation ("io.mockk:mockk-android:$mockAndroid")
    testImplementation ("io.mockk:mockk:$mockAndroid")



}