package config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.web.servlet.handler.HandlerMappingIntrospector
import security.handlers.ApiAccessDeniedHandler
import security.handlers.ApiEntryPoint
import security.jwt.JwtAuthenticationFilter
import security.jwt.JwtAuthorizationFilter
import javax.sql.DataSource

@EnableWebSecurity
@EnableMethodSecurity
@PropertySource("classpath:/application.properties")
class SecurityConfig(introspector: HandlerMappingIntrospector) {
    @Value("\${jwt.key}")
    private val jwtKey: String? = null
    private val mvc: MvcRequestMatcher.Builder

    init {
        mvc = MvcRequestMatcher.Builder(introspector)
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.apply(FilterConfigurer())

        http.csrf { it.disable() }

        http.exceptionHandling { configurer -> configurer
                .authenticationEntryPoint(ApiEntryPoint())
                .accessDeniedHandler(ApiAccessDeniedHandler())
        }

        http.authorizeHttpRequests { conf -> conf
                .requestMatchers(mvc.pattern("login")).permitAll()
                .requestMatchers(mvc.pattern("version")).permitAll()
                .requestMatchers(mvc.pattern("**")).authenticated()
        }

        return http.build()
    }

    inner class FilterConfigurer : AbstractHttpConfigurer<FilterConfigurer, HttpSecurity>() {
        override fun configure(http: HttpSecurity) {
            val manager = http.getSharedObject(AuthenticationManager::class.java)
            val loginFilter = JwtAuthenticationFilter(manager, "/api/login", jwtKey!!)
            http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter::class.java)

            val authorizationFilter = JwtAuthorizationFilter(jwtKey)
            http.addFilterBefore(authorizationFilter, AuthorizationFilter::class.java)
        }
    }

    @Bean
    fun userDetailService(dataSource: DataSource): UserDetailsService {
        return JdbcUserDetailsManager(dataSource)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(10)
    }
}