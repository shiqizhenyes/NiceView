// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    val kotlinVersion = "1.4.20"
    val supportVersion by extra("28.0.0")

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
//        classpath("com.github.dcendents:android-maven-gradle-plugin:2.0")
//        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.7.3")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://novoda.bintray.com/snapshots")
    }
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
