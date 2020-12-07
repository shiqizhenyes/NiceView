import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
}

android {

    compileSdkVersion(30)

    defaultConfig {
        applicationId("me.nice.view")
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*jar"))))
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation ("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
//    implementation("com.android.support.constraint:constraint-layout:2.0.4")
//    implementation("com.android.support:appcompat-v7:${rootProject.extra["supportVersion"]}")
//    implementation("com.android.support:recyclerview-v7:${rootProject.extra["supportVersion"]}")
//    implementation("com.android.support:design:${rootProject.extra["supportVersion"]}")
    implementation("net.qiujuer.genius:ui:2.1.1")
    implementation(project(path = ":view"))
}
