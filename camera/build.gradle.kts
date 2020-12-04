import org.jetbrains.kotlin.cli.jvm.main
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    kotlin("android")
//    kotlin("android.extensions")
}

android {
    compileSdkVersion(28)
    buildToolsVersion("28.0.3")
    defaultConfig {
        minSdkVersion(19)
        targetSdkVersion(26)
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
//        main {
//            java.srcDirs += ["src/main/kotlin"]
//        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*jar"))))
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}
