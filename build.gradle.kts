import com.google.cloud.tools.jib.gradle.JibTask

plugins {
    kotlin("jvm") version libs.versions.kotlin
    id("com.google.cloud.tools.jib") version "3.4.5"
}

group = "de.phyrone"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    implementation(libs.bundles.graalvm)

    testImplementation(kotlin("test"))
}

tasks {

    withType<JibTask>().configureEach {
        notCompatibleWithConfigurationCache("because https://github.com/GoogleContainerTools/jib/issues/3132")
    }
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
jib {
    from {
        image = "ghcr.io/graalvm/jdk-community:21"
        platforms {
            platform {
                this.os = "linux"
                this.architecture = "amd64"
            }
            platform {
                this.os = "linux"
                this.architecture = "arm64"
            }
        }
    }
    container {
        user = "1000:1000"
        this.labels = mapOf(
            "org.opencontainers.image.title" to "Discord Bot",
            "org.opencontainers.image.description" to "A simple discord bot",
            "org.opencontainers.image.version" to project.version.toString(),
            "org.opencontainers.image.source" to "https://github.com/Phyrone/discord-bot",
            "org.opencontainers.image.licenses" to "EUPL-1.2"
        )
    }
}