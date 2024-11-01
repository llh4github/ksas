package io.github.llh4github.ksas.library

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private lateinit var jwtService: JwtService

    @Test
    fun `create and valid jwt`() {
        val token = jwtService.createToken("Tom", 114514L) { keys ->
            mapOf<String, Any>(keys.ROLES_KEY to listOf("admin"))
        }
        val valid = jwtService.isValid(token)
        assertTrue(valid)
        val uid = jwtService.getUserId(token)
        assertEquals(114514L, uid)
    }
}
