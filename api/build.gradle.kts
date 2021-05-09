plugins {
    kotlin("jvm")
    kotlin("kapt")
}

group = "org.craftit.api"
version = "0.1-SNAPSHOT"

repositories {
}

dependencies {
    api(kotlin("reflect"))

    implementation("com.google.dagger:dagger:2.35.1")
    kapt("com.google.dagger:dagger-compiler:2.35.1")
}
