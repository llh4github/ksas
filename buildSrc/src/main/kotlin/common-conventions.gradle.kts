import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("io.gitlab.arturbosch.detekt")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
}

group = project.group.toString()
version = project.version.toString()


// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.findVersion("jdk").get().toString()))
    }
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
    // for ksp generated files
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    config.setFrom("$rootDir/config/detekt.yml")
//    baseline = file("$rootDir/config/baseline.xml")
}

tasks.withType<Detekt>().configureEach {
    exclude("**/build/generated/**", "**.kts")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
