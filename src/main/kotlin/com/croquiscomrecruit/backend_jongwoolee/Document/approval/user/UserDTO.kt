package com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth

import org.springframework.data.domain.Page
import javax.validation.constraints.NotBlank

data class ReqLogin(
        @field:NotBlank
        val id: String,
        @field:NotBlank
        val password: String
)

data class ResUser(
        val name: String
)
fun User.toDto() = ResUser(this.id)

data class ResUserPage(
        val count: Long,
        val page: Int,
        val users: List<ResUser>
)
fun Page<User>.toDto() = ResUserPage(
        this.totalElements,
        this.totalPages,
        this.content.map { it.toDto() }
)