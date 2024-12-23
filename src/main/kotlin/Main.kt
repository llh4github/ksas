package io.github.llh4github.ksas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
class Main

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<Main>(*args)
}
