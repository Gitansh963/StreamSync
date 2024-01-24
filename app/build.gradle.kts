plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.slayers.streamsync"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.slayers.streamsync"
        minSdk = 30
        targetSdk = 33
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
    packagingOptions {
        // Exclude the file from all dependencies
        exclude ("META-INF/DEPENDENCIES")
        // Or, merge the contents of the file from all dependencies
        // merge 'META-INF/DEPENDENCIES'
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation ("com.google.android.material:material:1.10.0")
    implementation ("com.google.android.exoplayer:exoplayer:2.19.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.media3:media3-exoplayer:1.1.1")
    implementation("androidx.media3:media3-ui:1.1.1")
    testImplementation("junit:junit:4.13.2")
    implementation ("androidx.fragment:fragment-ktx:1.3.6")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.google.http-client:google-http-client-android:1.39.2")

    implementation ("com.google.apis:google-api-services-youtube:v3-rev20201214-1.31.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")

}
