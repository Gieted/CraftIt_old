plugins {
    kotlin("jvm")
    kotlin("kapt")
}

group = "org.craftit.api"
version = "0.1-SNAPSHOT"

repositories {
}

dependencies {
    implementation("com.google.dagger:dagger:2.37")
    kapt("com.google.dagger:dagger-compiler:2.37")
}
