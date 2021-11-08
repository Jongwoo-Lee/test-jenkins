package com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth

import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.*
import java.util.*

@Entity
data class User(
       @Id
       val id: String,
       val password: String ?= null,

       @Temporal(TemporalType.TIMESTAMP)
       val regDate: Date ?= null
)

interface UserRepository: JpaRepository<User, String> {}