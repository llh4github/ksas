plugins {
    id("common-conventions")
    alias(libs.plugins.ksp)
}

dependencies {
    ksp(libs.jimmer.ksp)
    compileOnly(libs.jimmer.sql.kotlin)
    compileOnly(libs.swagger.annotations)
    compileOnly(libs.validator)
    implementation(project(":ksas-commons"))
}
