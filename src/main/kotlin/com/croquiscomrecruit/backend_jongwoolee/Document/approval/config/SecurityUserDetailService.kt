package com.croquiscomrecruit.backend_jongwoolee.Document.approval.config

import com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class SecurityUserDetailService @Autowired constructor(
        val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        val userOpt = userRepository.findById(username)
        if(userOpt.isPresent) return SecurityUserDetail(userOpt.get().id, userOpt.get().password?:"")
        else throw UsernameNotFoundException("User not found with username: $username")
    }
}