plugins {
    id("common-conventions")
    alias(libs.plugins.ksp)
}

dependencies {
    ksp(libs.jimmer.ksp)
    compileOnly(libs.jimmer.sql.kotlin)
    compileOnly(libs.swagger.annotations)
    implementation(libs.validator)
    implementation(project(":ksas-commons"))
}
