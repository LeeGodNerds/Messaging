plugins {
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
}

subprojects {
    apply {
        plugin("java")
        plugin("com.github.johnrengelman.shadow")
        plugin("maven-publish")
    }

    group = "io.fairyproject.messaging"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    val compileOnly by configurations
    val annotationProcessor by configurations
    val testCompileOnly by configurations
    val testAnnotationProcessor by configurations
    val testImplementation by configurations
    val testRuntimeOnly by configurations

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

        compileOnly("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok:1.18.24")

        testCompileOnly("org.projectlombok:lombok:1.18.24")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.24")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                from(components["java"])
            }
        }
    }
}