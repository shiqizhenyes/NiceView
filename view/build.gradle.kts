import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    kotlin("android")
//    kotlin("android.extensions")
//    id("com.github.dcendents.android-maven")
//    id("com.jfrog.bintray")
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

//apply plugin: 'com.github.dcendents.android-maven'
//apply plugin: 'com.jfrog.bintray'

//def siteUrl = 'https://github.com/shiqizhenyes/NiceView' // 项目主页。
//def gitUrl = 'https://github.com/shiqizhenyes/NiceView.git'


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*jar"))))
    implementation("com.google.android.material:material:1.2.1")
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    implementation("androidx.core:core:1.3.2")
    implementation("androidx.interpolator:interpolator:1.0.0")
    implementation ("androidx.appcompat:appcompat:1.2.0")
//    implementation("com.android.support:design:${rootProject.extra["supportVersion"]}")
}

// 生成jar包的task，不需要修改。
//task sourcesJar(type: Jar) {
//    from android.sourceSets.main.java.srcDirs
//    classifier = 'sources'
//}

// 生成jarDoc的task，不需要修改。
//task javadoc(type: Javadoc) {
//    source = android.sourceSets.main.java.srcDirs
//    classpath += project.files(android.getBootClasspath().join(File.pathSeparator))
//    failOnError false // 忽略注释语法错误，如果用jdk1.8你的注释写的不规范就编译不过。
//}

// 生成javaDoc的jar，不需要修改。
//task javadocJar(type: Jar, dependsOn: javadoc) {
//    classifier = 'javadoc'
//    from javadoc.destinationDir
//}

//artifacts {
//    archives javadocJar
//    archives sourcesJar
//}

//group = "me.nice.view"
//version = "1.1.7"

//install {
//    repositories.mavenInstaller {
//        // 生成pom.xml和参数
//        pom {
//            project {
//                packaging 'aar'
//                // 项目描述，复制我的话，这里需要修改。
//                name 'nice view'// 可选，项目名称。
//                description 'nice view open source library'// 可选，项目描述。
//                url siteUrl // 项目主页，这里是引用上面定义好。
//
//                // 软件开源协议，现在一般都是Apache License2.0吧，复制我的，这里不需要修改。
//                licenses {
//                    license {
//                        name 'MIT'
//                        url 'https://mit-license.org/'
//                    }
//                }
//
//                //填写开发者基本信息，复制我的，这里需要修改。
//                developers {
//                    developer {
//                        id 'shiqizhenyes' // 开发者的id。
//                        name 'shiqizhenyes' // 开发者名字。
//                        email 'shiqizhenyes@gmail.com' // 开发者邮箱。
//                    }
//                }
//
//                // SCM，复制我的，这里不需要修改。
//                scm {
//                    connection gitUrl // Git仓库地址。
//                    developerConnection gitUrl // Git仓库地址。
//                    url siteUrl // 项目主页。
//                }
//            }
//        }
//    }
//}
//
//Properties properties = new Properties()
//properties.load(project.rootProject.file('gradle.properties').newDataInputStream())
//bintray {
//    user = properties.getProperty("bintray.user")
//    key = properties.getProperty("bintray.apikey")
//    publish = true
//    configurations = ['archives']
//    pkg {
//        repo = 'NiceView'
//        name = 'NiceView'
//        vcsUrl = 'https://github.com/shiqizhenyes/NiceView.git'
//        websiteUrl = 'https://github.com/shiqizhenyes/NiceView'
//        licenses = ['MIT']
//        issueTrackerUrl = 'https://github.com/shiqizhenyes/NiceView/issues'
//        publicDownloadNumbers = true
//        version {
//            name = '1.1.7'
//            desc = 'nice view open source library'
//            vcsTag = '1.1.7'
//            attributes = ['gradle-plugin': 'com.use.less:com.use.less.gradle:gradle-useless-plugin']
//        }
//    }
//}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}

tasks.withType(Javadoc::class) {//防止编码问题
    options.encoding ="UTF-8"
}