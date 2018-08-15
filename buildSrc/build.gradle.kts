plugins {
    kotlin("jvm") version ("1.2.60")
}

buildscript {

    repositories {
        gradlePluginPortal()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", "1.2.60"))
    }
}

dependencies {
    implementation(gradleKotlinDsl())
    implementation(kotlin("stdlib-jdk8", "1.2.60"))
}

repositories {
    gradlePluginPortal()
}