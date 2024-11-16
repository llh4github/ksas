plugins {
    id("common-conventions")
    id("spring-conventions")
    alias(libs.plugins.ksp)
    id("org.graalvm.buildtools.native")
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
//val graal by sourceSets.creating
//
//graal.kotlin {
//    srcDir("src/main/kotlin")
//    srcDir("build/generated/ksp/main/kotlin")
//}
//
//configurations {
//    nativeImageClasspath.extendsFrom(getByName(""))
//}

dependencies {
//    "graalCompileOnly"("org.graalvm.nativeimage:svm:21.2.0")
//    "graalCompileOnly"("org.graalvm.sdk:graal-sdk:21.2.0")
//    nativeImageCompileOnly(graal.output.classesDirs)

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation(libs.jimmer.starter)
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation("org.springframework.security:spring-security-test")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(libs.nacos.cloud.config)
    implementation(libs.knife4j.openapi3)
    runtimeOnly("org.postgresql:postgresql")
    implementation(libs.jjwt.api)
    implementation(libs.jjwt.impl)
    implementation(libs.jjwt.jackson)

    ksp(libs.jimmer.ksp)

    implementation(project(":ksas-commons"))
    implementation(project(":ksas-db-model"))
}

//graalvmNative {
//    binaries {
//        named("main") {
//            configurationFileDirectories.from(file("build/generated/ksp/main/kotlin"))
//        }
//
//    }
//}