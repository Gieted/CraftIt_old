plugins {
    kotlin("jvm") version "1.5.0" apply false
}

group = "org.craftit"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

val jvmProjects = listOf("api", "runtime", "test_plugin").map { project(it) }

jvmProjects.forEach {
    it.repositories {
        mavenCentral()
    }

    it.apply(plugin = "org.jetbrains.kotlin.jvm")
    it.apply(plugin = "idea")

    val generatedKotlinDir = it.buildDir.resolve("generated/source/kotlin/main")

    it.configure<SourceSetContainer> {
        val main by getting

        main.java {
            setSrcDirs(srcDirs + generatedKotlinDir)
        }
    }

    it.configure<org.gradle.plugins.ide.idea.model.IdeaModel> {
        module {
            generatedSourceDirs.add(generatedKotlinDir)
        }
    }

    it.dependencies {
        val implementation by it.configurations
        val testImplementation by it.configurations

        implementation(kotlin("stdlib"))

        val kotestVersion = "4.5.0.RC1"
        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    }

    it.tasks.withType<Test> {
        useJUnitPlatform()
    }
}
