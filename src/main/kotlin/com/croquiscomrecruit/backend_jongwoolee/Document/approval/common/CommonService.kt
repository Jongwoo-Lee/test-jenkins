package com.croquiscomrecruit.backend_jongwoolee.Document.approval.common

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommonService @Autowired constructor(
        val categoryRepository: CategoryRepository
) {

    fun getAllCategories() = categoryRepository.findAll()
}