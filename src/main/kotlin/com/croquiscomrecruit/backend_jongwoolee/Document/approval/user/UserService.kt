package com.croquiscomrecruit.backend_jongwoolee.Document.approval.user

import com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth.ResUser
import com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth.ResUserPage
import com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth.UserRepository
import com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth.toDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
        val userRepository: UserRepository
) {

    fun getUsersPage(pageNum: Int = 0, pageSize: Int = 10) =
            userRepository.findAll(
                PageRequest.of(pageNum, pageSize, Sort.Direction.ASC, "id")
            ).toDto()

}