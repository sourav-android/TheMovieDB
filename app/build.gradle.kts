plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id ("com.google.devtools.ksp")
    id("androidx.navigation.safeargs")


}

android {
    namespace = "com.android.themoviedb"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.themoviedb"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "TMDB_KEY",project.properties["TMDB_KEY"].toString())
        buildConfigField("String", "TMDB_BASE_URL",project.properties["TMDB_BASE_URL"].toString())
        buildConfigField("String", "MOVIE_POSTER_URL",project.properties["MOVIE_POSTER_URL"].toString())


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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {


    /*implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)*/

    val retrofitVersion = "2.9.0"
    val koinVersion = "3.5.3"
    val lifecycleVersion = "2.7.0"
    val archVersion = "2.2.0"
    val roomVersion = "2.6.1"
    val navVersion = "2.7.7"
    val pagingVersion = "3.2.1"
    val paperVersion = "2.7.2"
    val lottieVersion = "6.0.0"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    // For testing Koin
    testImplementation ("io.insert-koin:koin-test-junit4:$koinVersion")
    // For testing LiveData
    testImplementation("androidx.arch.core:core-testing:$archVersion")
    // For testing Lifecycle in runtime
    testImplementation ("androidx.lifecycle:lifecycle-runtime-testing:$lifecycleVersion")
    // For testing Room
    testImplementation ("androidx.room:room-testing:$roomVersion")
    // Truth for assertions
    testImplementation ("com.google.truth:truth:1.4.2")
    // For testing API in  Mock Web Server
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.9.0")
    // Mockito
    testImplementation ("org.mockito:mockito-core:2.28.2")


    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    // For testing LiveData dependencies
    androidTestImplementation ("androidx.arch.core:core-testing:2.1.0")
    // For testing Coroutine dependencies
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    // Truth for assertions
    androidTestImplementation ("com.google.truth.extensions:truth-java8-extension:1.1.3")

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation( "androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // ViewModelScope
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    // LifecycleScope
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    // Lifecycle utilities for Compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")

    // Room
    implementation ("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation ("androidx.room:room-ktx:$roomVersion")
    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$roomVersion")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // Koin
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-navigation:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")
    implementation("io.insert-koin:koin-annotations:1.3.0")

    //Paging 3
    implementation ("androidx.paging:paging-runtime:$pagingVersion")
    implementation ("androidx.paging:paging-compose:3.3.0-alpha02")

    // Gson Serialization
    implementation("com.google.code.gson:gson:2.10.1")
    // Splash Api
    implementation ("androidx.core:core-splashscreen:1.0.1")
    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Paper
    implementation("io.github.pilgr:paperdb:$paperVersion")
    // Lottie
    implementation("com.airbnb.android:lottie-compose:$lottieVersion")


    /*implementation("com.github.bumptech.glide:glide:4.14.2")
    ksp("com.github.bumptech.glide:ksp:4.14.2")*/



}