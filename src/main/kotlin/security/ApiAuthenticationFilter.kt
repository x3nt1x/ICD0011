package security

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import security.handlers.ApiAuthFailureHandler
import security.handlers.ApiAuthSuccessHandler
import java.io.IOException
import java.util.stream.Collectors

open class ApiAuthenticationFilter(authenticationManager: AuthenticationManager, url: String) : AbstractAuthenticationProcessingFilter(url) {
    init {
        super.setAuthenticationManager(authenticationManager)
        super.setAuthenticationSuccessHandler(ApiAuthSuccessHandler())
        super.setAuthenticationFailureHandler(ApiAuthFailureHandler())
        super.setSecurityContextRepository(HttpSessionSecurityContextRepository())
    }

    @Throws(IOException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val content = request.reader.lines().collect(Collectors.joining())

        val credentials: LoginCredentials = try {
            ObjectMapper().readValue(content, LoginCredentials::class.java)
        } catch (exception: JsonProcessingException) {
            throw BadCredentialsException("", exception)
        }

        val token = UsernamePasswordAuthenticationToken(credentials.userName, credentials.password)

        return authenticationManager.authenticate(token)
    }
}