plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "me.trup10ka.puby"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    /* Kord Library */
    implementation(libs.kord.core)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
