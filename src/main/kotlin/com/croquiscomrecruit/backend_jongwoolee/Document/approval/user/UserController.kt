package com.croquiscomrecruit.backend_jongwoolee.Document.approval.user

import com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth.ResUserPage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@RestController
@RequestMapping("/api/user")
class UserController @Autowired constructor(
        val userService: UserService
) {
    @GetMapping("/page")
    fun getUsersPage(
            @RequestParam pageNum: Int?,
            @RequestParam pageSize: Int?
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(
                    userService.getUsersPage(pageNum = pageNum ?: 0, pageSize= pageSize ?: 10)
            )
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}