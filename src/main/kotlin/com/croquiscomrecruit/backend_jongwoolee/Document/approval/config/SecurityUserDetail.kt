package com.croquiscomrecruit.backend_jongwoolee.Document.approval.config

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SecurityUserDetail(val id: String, val pw: String) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

    override fun isEnabled() = true

    override fun getUsername() = id

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = pw

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}