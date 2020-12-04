plugins {
    id("com.android.application")
    kotlin("android")
//    kotlin("android.extensions")
}

android {

    compileSdkVersion(28)

    defaultConfig {
        applicationId("me.nice.view")
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
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*jar"))))
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
    implementation("com.android.support.constraint:constraint-layout:2.0.4")
    implementation("com.android.support:appcompat-v7:$")
    implementation("com.android.support:recyclerview-v7:${rootProject.extra["supportVersion"]}")
    implementation("com.android.support:design:${rootProject.extra["supportVersion"]}")
    implementation("net.qiujuer.genius:ui:2.1.1")
    implementation(project(path = ":view"))
}
