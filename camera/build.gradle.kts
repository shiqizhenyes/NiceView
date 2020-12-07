import org.jetbrains.kotlin.config.KotlinCompilerVersion



plugins {
    id("com.android.library")
    kotlin("android")
//    kotlin("android.extensions")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("28.0.3")
    defaultConfig {
        minSdkVersion(22)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        getByName("main") {
            java {
                srcDir("src/main/java")
                srcDir("src/main/kotlin")
            }
        }
    }

}

dependencies {
    val kotlinVersion = "1.4.20"
    val cameraXVersion = "1.0.0-beta12"
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*jar"))))
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation ("androidx.appcompat:appcompat:1.2.0")
    implementation ("com.google.android.material:material:1.2.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.4")
    implementation ("androidx.camera:camera-camera2:$cameraXVersion")
    implementation ("androidx.camera:camera-lifecycle:$cameraXVersion")
    implementation ("androidx.camera:camera-view:1.0.0-alpha19")

}
