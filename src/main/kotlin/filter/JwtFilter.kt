package io.github.llh4github.ksas.filter

import io.github.llh4github.ksas.commons.property.WebSecurityProperty
import io.github.llh4github.ksas.library.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

/**
 * JWT过滤器
 */
class JwtFilter(
    private val property: WebSecurityProperty,
    private val jwtService: JwtService,
) : OncePerRequestFilter() {

    @Suppress("MaxLineLength")
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        SecurityContextHolder.clearContext()
        extractJwt(request)?.let {
            jwtService.validAndAuthBo(it)?.let { bo ->
                SecurityContextHolder.getContext().authentication = bo
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun extractJwt(request: HttpServletRequest): String? {
        val header = request.getHeader(property.jwtHeaderName)
        val prefix = property.jwtHeaderPrefix

        return if (header != null && header.startsWith(prefix)) {
            header.substring(prefix.length)
        } else {
            null
        }
    }
}
