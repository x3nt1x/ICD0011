package security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthorizationFilter(private val jwtKey: String) : OncePerRequestFilter() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val tokenString = request.getHeader("Authorization")

        if (tokenString == null) {
            chain.doFilter(request, response)
            return
        }

        val (username, roles) = JwtHelper(jwtKey).decode(tokenString)
        val authorities = roles.map { SimpleGrantedAuthority(it) }
        val springToken = UsernamePasswordAuthenticationToken(username, null, authorities)

        SecurityContextHolder.getContext().authentication = springToken

        chain.doFilter(request, response)
    }
}