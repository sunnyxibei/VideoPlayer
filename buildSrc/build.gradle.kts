apply {
    plugin("kotlin")
}

buildscript {

    repositories {
        gradlePluginPortal()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", "1.2.50"))
    }
}

dependencies {
    implementation(gradleKotlinDsl())
    implementation(kotlin("stdlib", "1.2.50"))
}

repositories {
    gradlePluginPortal()
}