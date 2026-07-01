plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "com.tpk.puby"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    /* Kord Library */
    implementation(libs.kord.core)

    /* Config Library */
    implementation(libs.hoplite.core)
    implementation(libs.hoplite.hocon)

    /* Utilities */
    implementation(libs.kotlin.logging.jvm)
    implementation(libs.logback.classic)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
