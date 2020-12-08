import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
//    kotlin("android.extensions")
}

android {

    compileSdkVersion(30)

    defaultConfig {
        applicationId("me.nice.view.samples")
        minSdkVersion(22)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
                setSrcDirs(listOf("src/main/java", "src/main/kotlin"))
            }
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {
    val kotlinVersion = "1.4.20"
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation ("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("net.qiujuer.genius:ui:2.1.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-common-java8:2.2.0")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.4")
    implementation(project(mapOf("path" to ":camera")))
    implementation(project(mapOf("path" to ":view")))
}
