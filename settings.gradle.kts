plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "ksas"
include("ksas-commons")
include("ksas-db-model")
include("ksas-web")
