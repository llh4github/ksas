plugins {
    id("common-conventions")
    `java-library`
}

dependencies {
    api(libs.oshai.kotlinLogging)
    compileOnly(libs.swagger.annotations)
    compileOnly(libs.slf4j.api)
    compileOnly(libs.logback.core)
    compileOnly(libs.logback.classic)
}
