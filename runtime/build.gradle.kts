plugins {
    kotlin("jvm")
    kotlin("kapt")
}

group = "org.craftit.runtime"
version = "0.1-SNAPSHOT"

repositories {
    maven (url = "https://libraries.minecraft.net")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":test_plugin"))
    implementation("org.javassist:javassist:3.27.0-GA")

    implementation("com.google.dagger:dagger:2.35.1")
    kapt("com.google.dagger:dagger-compiler:2.35.1")

    implementation("com.google.code.gson:gson:2.8.6")

    implementation("com.mojang:brigadier:1.0.17")
}
