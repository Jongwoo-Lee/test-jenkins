package com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import java.lang.RuntimeException
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid


@RestController
class AuthController @Autowired constructor(
        val userRepository: UserRepository,
        val authService: AuthService,
        @Value("\${jwt.header}") val tokenHeader: String
) {
    @CrossOrigin(origins = ["*"])
    @PostMapping(value=["/auth/login"])
    fun login(
            @RequestBody @Valid param: ReqLogin,
            httpServletResponse: HttpServletResponse
    ): ResponseEntity<Any> {
        try {
            authService.checkLogin(param.id, param.password)
        } catch (e: Exception) {
            return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }

        httpServletResponse.addCookie(authService.generateCookie(param.id))

        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @CrossOrigin(origins = ["*"])
    @PostMapping(value=["/auth/join"])
    fun join(
            @RequestBody @Valid param: ReqLogin,
            httpServletResponse: HttpServletResponse
    ): ResponseEntity<Any> {
        if(userRepository.existsById(param.id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        userRepository.save(authService.userParamToEntity(param))

        return login(param, httpServletResponse)
    }

    @PostMapping(value=["/api/logout"])
    fun logout(res: HttpServletResponse): ResponseEntity<Any> {
        val out = Cookie(tokenHeader, null).apply {
            path = "/"
            maxAge = 0
            isHttpOnly = true
        }

        res.addCookie(out)
        return ResponseEntity.ok().build<Any>()
    }
}