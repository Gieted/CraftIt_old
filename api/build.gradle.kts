plugins {
    kotlin("jvm")
    kotlin("kapt")
}

group = "org.craftit.api"
version = "0.1-SNAPSHOT"

repositories {
    maven(url = "https://libraries.minecraft.net")
}

dependencies {
    implementation("com.google.dagger:dagger:2.38")
    kapt("com.google.dagger:dagger-compiler:2.38")

    implementation("com.mojang:brigadier:1.0.18")
}
