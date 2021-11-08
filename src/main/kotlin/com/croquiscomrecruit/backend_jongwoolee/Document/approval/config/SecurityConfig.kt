package com.croquiscomrecruit.backend_jongwoolee.Document.approval.config

import com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth.UserRepository
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.WebUtils
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig @Autowired constructor(
        val securityUserDetailService: SecurityUserDetailService,
        @Value("\${jwt.header}") val tokenHeader: String,
        @Value("\${jwt.secret}") val secret: String,
) : WebSecurityConfigurerAdapter() {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun corsConfig(): CorsConfigurationSource {
        val config = CorsConfiguration().apply {
            addAllowedOrigin("*")
            addAllowedHeader("*")
            addAllowedMethod("*")
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }
    }


    @Autowired
    fun configureGlobal(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
                .userDetailsService(securityUserDetailService)
                .passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.cors().configurationSource(corsConfig()).and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint{_, response, _ ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED")
                }.and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/auth/**").permitAll().anyRequest().authenticated()

        val filter = object: OncePerRequestFilter() {
            override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
                val cookie = WebUtils.getCookie(request, tokenHeader)
                if(cookie != null) {
                    val requestToken = cookie.value
                    val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(requestToken).body
                    val username = claims.subject
                    if (username != null && SecurityContextHolder.getContext().authentication == null) {
                        val userDetails = userDetailsService().loadUserByUsername(username)
                        if (userDetails != null && username == userDetails.username && !claims.expiration.before(Date())) {
                            val authenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                            SecurityContextHolder.getContext().authentication = authenticationToken
                        }
                    }
                }
                filterChain.doFilter(request, response)
            }
        }
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
        http.headers().frameOptions().sameOrigin().cacheControl()
    }

    override fun configure(web: WebSecurity) {
        super.configure(web)
        web.ignoring().antMatchers(
            HttpMethod.GET,
            "/",
            "/css/**",
            "/js/**",
            "/*.ico",
            "/*.html"
        )
    }
}
