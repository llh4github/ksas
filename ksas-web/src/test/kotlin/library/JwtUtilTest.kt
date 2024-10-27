package io.github.llh4github.ksas.library

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Test
    fun `create and valid jwt`() {
        val userId = "bbaacc"
        val token = jwtUtil.createToken { keys ->
            mapOf<String, Any>(keys.USER_ID_KEY to userId)
        }
        val valid = jwtUtil.isValid(token)
        assertTrue(valid)
        jwtUtil.parse(token)?.let {
            val uid = it[JwtUtil.JwtKeys.USER_ID] as String
            println(uid)
            assertEquals(userId, uid)
        }
    }
}
