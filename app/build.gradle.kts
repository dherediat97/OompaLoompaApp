plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.dherediat97.oompaloompaapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dherediat97.oompaloompaapp"
        minSdk = 24
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
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            matchingFallbacks += listOf("release")
            isDebuggable = false
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
    //Android Core KTX
    implementation("androidx.core:core-ktx:1.12.0")
    //Android Lifecycle Runtime KTX
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0-rc01")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.0")
    debugImplementation("androidx.test:core:1.5.0")

    //Compose Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    //Compose Material Icons Extended
    implementation("androidx.compose.material:material-icons-extended:1.6.0")

    //Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //Retrofit Converter GSON
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //OK HTTP Logging Interecptor(More info in the api requests)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //KOIN Android Core
    implementation("io.insert-koin:koin-android:3.5.3")

    //KOIN FOR COMPOSE
    implementation("io.insert-koin:koin-androidx-compose:3.5.3")

    //KOIN FOR TESTS
    testImplementation("io.insert-koin:koin-test:3.5.3")
    testImplementation("io.insert-koin:koin-test-junit4:3.5.3")

    //COIL COMPOSE
    implementation("io.coil-kt:coil-compose:2.5.0")

    //Lottie
    implementation("com.airbnb.android:lottie-compose:6.3.0")
}