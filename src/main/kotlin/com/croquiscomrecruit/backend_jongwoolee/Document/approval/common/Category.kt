package com.croquiscomrecruit.backend_jongwoolee.Document.approval.common

import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Category(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        val name: String? = null,
)

interface CategoryRepository: JpaRepository<Category, Int> {}