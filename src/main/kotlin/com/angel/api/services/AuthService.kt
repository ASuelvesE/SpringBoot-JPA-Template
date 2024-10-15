package com.angel.api.services

import com.angel.api.models.enums.Role
import com.angel.api.models.User
import org.springframework.stereotype.Service

@Service
interface AuthService {
    fun createUser(name: String, surnames: String, email: String, rawPassword: String, role: Role): User
    fun findByEmail(email: String): User
}
