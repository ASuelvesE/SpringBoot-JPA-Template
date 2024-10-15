package com.angel.api.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

object Utils {
    private val passwordEncoder = BCryptPasswordEncoder()

    fun encodePassword(rawPassword: String): String {
        return passwordEncoder.encode(rawPassword)
    }

    fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }
}
