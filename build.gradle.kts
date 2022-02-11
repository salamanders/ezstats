plugins {
    kotlin("jvm") version "1.6.10"
    java
    `maven-publish`
}

group = "info.benjaminhill"
version = "1.0-SNAPSHOT"
description = "ezStats"

repositories {
    mavenLocal()
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        url = uri("https://repository.apache.org/snapshots")
    }
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
    implementation("org.nield:kotlin-statistics:1.2.1")
    implementation("org.apache.commons:commons-math4:4.0-SNAPSHOT")
    implementation("com.github.salamanders:utils:35760e7462")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")

    implementation("com.github.jitpack:gradle-simple:1.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")

}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        afterEvaluate {
            artifactId = tasks.jar.get().archiveBaseName.get()
        }
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
        jvmTarget = "1.8"
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    withJavadocJar()
    withSourcesJar()
}