package security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import security.ApiAuthenticationFilter
import security.TokenInfo

class JwtAuthenticationFilter(authenticationManager: AuthenticationManager, url: String, private val jwtKey: String) : ApiAuthenticationFilter(authenticationManager, url) {
    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        val user = authResult.principal as User
        val roles = user.authorities.map(GrantedAuthority::getAuthority)
        val token = JwtHelper(jwtKey).encode(TokenInfo(user.username, roles))

        response.addHeader("Authorization", "Bearer $token")
    }
}