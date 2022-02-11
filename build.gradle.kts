plugins {
    java
    `maven-publish`
}

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
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
    implementation("org.nield:kotlin-statistics:1.2.1")
    implementation("org.apache.commons:commons-math4:4.0-SNAPSHOT")
    implementation("com.github.salamanders:utils:35760e7462")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.6.10")
}

group = "info.benjaminhill"
version = "1.0-SNAPSHOT"
description = "ezStats"
java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
