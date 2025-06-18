plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.homework.shopapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.homework.shopapp"
        minSdk = 24
        targetSdk = 35
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Add this line for RecyclerView:
    implementation("androidx.recyclerview:recyclerview:1.4.0")

    // Add CardView for better UI
    implementation("androidx.cardview:cardview:1.0.0")

    // Add Material Design components
    implementation("com.google.android.material:material:1.11.0")




    implementation(libs.gson)


    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}