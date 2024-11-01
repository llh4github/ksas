package io.github.llh4github.ksas.filter

import io.github.llh4github.ksas.config.WebSecurityProperty
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

/**
 * JWT过滤器
 */
class JwtFilter(
    private val property: WebSecurityProperty
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        extractJwt(request)?.let {
            // TODO 验证jwt
        }
    }

    private fun extractJwt(request: HttpServletRequest): String? {
        val header = request.getHeader(property.jwtHeaderName)
        return if (header != null && header.startsWith(property.jwtPrefix)) {
            header.substring(property.jwtPrefix.length)
        } else {
            null
        }
    }
}
