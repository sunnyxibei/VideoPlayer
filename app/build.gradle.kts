import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import org.jetbrains.kotlin.config.AnalysisFlag.Flags.experimental

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
        multiDexEnabled = true
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        ndk {
            abiFilter("armeabi-v7a")
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    androidExtensions {
        isExperimental = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    testImplementation(deps.test.junit)
    testImplementation(deps.test.runner)
    testImplementation(deps.test.espressoCore)

    implementation(deps.kotlin.stdlib)
    implementation(deps.support.compat)
    implementation(deps.support.design)
    implementation(deps.support.cardview)
    implementation(deps.support.constraint)
    implementation(deps.others.glide)
    implementation(deps.others.okhttp)
    implementation(deps.others.gson)
    implementation(deps.others.anko)
    implementation(deps.others.bottombar)
    implementation(deps.others.glideTransformations)
    implementation(deps.others.ijkplayer)
    implementation("com.android.support:multidex:1.0.1")
    implementation ("cn.jzvd:jiaozivideoplayer:6.2.12")
}