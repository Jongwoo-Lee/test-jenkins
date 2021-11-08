package com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.Cookie

@Service
class AuthService @Autowired constructor(
        val passwordEncoder: PasswordEncoder,
        val authenticationManager: AuthenticationManager,
        @Value("\${jwt.secret}") val secret: String,
        @Value("\${jwt.header}") val tokenHeader: String,
        @Value("\${jwt.expiration}") val expiration: Int
){
    fun checkLogin(id: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(id, password))
        } catch (e: Exception) {
            throw e
        }
    }

    fun generateToken(id: String): String {
        val start = Date()
        return Jwts.builder()
                .setClaims(mutableMapOf())
                .setSubject(id)
                .setIssuedAt(start)
                .setExpiration(Date(start.time + (expiration * 1000)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    fun generateCookie(id: String) = Cookie(tokenHeader, generateToken(id)).apply {
        path = "/"
        maxAge = expiration
        isHttpOnly = true
    }

    fun userParamToEntity(reqLogin: ReqLogin) =
            User(reqLogin.id, passwordEncoder.encode(reqLogin.password), Date())
}