package io.github.llh4github.ksas.library

import org.junit.jupiter.api.Assertions.*
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
        jwtService.validAndGetUserId(token)?.let {
            assertEquals(114514L, it)
        } ?: fail("validAndGetUserId return null")
    }

    @Test
    @Suppress("MaxLineLength")
    fun `Validate JWT generated by other platforms`() {
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
        // 会出现签名验证失败的异常
        val rs = jwtService.isValid(token)
        assertFalse(rs)
    }
}
