plugins {
    kotlin("jvm")
}

group = "org.craftit.test_plugin"
version = "0.1-SNAPSHOT"

repositories {
}

dependencies {
    compileOnly(project(":api"))
}

tasks.jar {
    archiveExtension.set("craftit")
    finalizedBy("deploy")
}

tasks.create<Copy>("deploy") {
    from(tasks.jar)
    into(rootDir.resolve("test_servers/vanilla/1.16.5/plugins"))
}
