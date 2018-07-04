import com.android.build.gradle.ProguardFiles.getDefaultProguardFile

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
            path = File(rootDir, "app/CMakeLists.txt")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*jar", "*aar"))))
    testImplementation(deps.test.junit)
    testImplementation(deps.test.runner)
    testImplementation(deps.test.espressoCore)

    implementation(deps.kotlin.stdlibJdk7)
    implementation(deps.support.compat)
    implementation(deps.support.design)
    implementation(deps.support.cardview)
    implementation(deps.support.constraint)
    implementation(deps.others.glide)
    implementation(deps.others.okhttp)
    implementation(deps.others.gson)
    implementation(deps.others.anko)
    implementation(deps.others.bottombar)
}