plugins {
    id("common-conventions")
    id("spring-conventions")
    alias(libs.plugins.ksp)
}

group = "io.github.llh4github.ksas"
version = file("project.version").readLines()[0]

dependencies {
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

    implementation(libs.oshai.kotlinLogging)
    implementation(libs.yitter.idgenerator)
    ksp(libs.jimmer.ksp)
    implementation(libs.apache.io)
    implementation(libs.minio)
}
