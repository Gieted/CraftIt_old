plugins {
    kotlin("jvm")
}

group = "org.craftit.api"
version = "0.1-SNAPSHOT"

repositories {
    maven(url = "https://libraries.minecraft.net")
}

dependencies {
    implementation("com.mojang:brigadier:1.0.18")
}
