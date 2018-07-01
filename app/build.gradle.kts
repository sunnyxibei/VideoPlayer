extra.set("kotlin_version", "1.2.50")
extra.set("anko_version", "0.10.5")
val kotlin_version: String by extra
val anko_version: String by extra

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(27)
    defaultConfig {
        applicationId = "cn.com.timeriver.videoplayer"
        minSdkVersion(21)
        targetSdkVersion(27)
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags.add("")
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    externalNativeBuild {
        cmake {
            path = File(rootDir,"app/CMakeLists.txt")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version")
    implementation("com.android.support:appcompat-v7:27.1.1")
    implementation ("org.jetbrains.anko:anko:$anko_version")
    implementation("com.android.support.constraint:constraint-layout:1.1.2")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}