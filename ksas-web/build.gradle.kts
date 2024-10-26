plugins {
    id("common-conventions")
    id("spring-conventions")
    alias(libs.plugins.ksp)
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation(libs.jimmer.starter)
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(libs.knife4j.openapi3)
    runtimeOnly("org.postgresql:postgresql")

    ksp(libs.jimmer.ksp)

    implementation(project(":ksas-commons"))
    implementation(project(":ksas-db-model"))
}
