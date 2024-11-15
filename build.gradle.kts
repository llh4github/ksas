group = "io.github.llh4github.ksas"
version = file("project.version").readLines()[0]

allprojects {
    repositories {
        maven {
            setUrl("https://maven.aliyun.com/repository/central")
        }
        maven {
            setUrl("https://maven.aliyun.com/repository/jcenter")
        }
        maven {
            setUrl("https://maven.aliyun.com/repository/google")
        }
        maven {
            setUrl("https://maven.aliyun.com/repository/gradle-plugin")
        }
        google()
        mavenCentral()
    }
}