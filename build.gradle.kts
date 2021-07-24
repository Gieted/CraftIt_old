plugins {
    kotlin("jvm") version "1.5.21" apply false
}

group = "org.craftit"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

val jvmProjects = listOf("runtime", "api", "test_plugin").map { project(it) }

jvmProjects.forEach {
    it.run {
        repositories {
            mavenCentral()
        }

        apply(plugin = "org.jetbrains.kotlin.jvm")
        apply(plugin = "idea")

        val generatedKotlinDir = buildDir.resolve("generated/source/kotlin/main")

        configure<SourceSetContainer> {
            val main by getting

            main.java {
                setSrcDirs(srcDirs + generatedKotlinDir)
            }
        }

        configure<org.gradle.plugins.ide.idea.model.IdeaModel> {
            module {
                generatedSourceDirs.add(generatedKotlinDir)
            }
        }

        dependencies {
            val implementation by configurations
            val testImplementation by configurations

            implementation(kotlin("stdlib"))

            val kotestVersion = "4.6.1"
            testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
            testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
            
            testImplementation("io.mockk:mockk:1.12.0")
        }

        tasks.withType<Test> {
            useJUnitPlatform()
        }
    }
}
