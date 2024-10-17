package com.angel.api.services

import com.angel.api.models.enums.Role
import com.angel.api.models.User
import com.angel.api.models.dto.UserDTO
import com.angel.api.models.dto.request.LoginRequest
import com.angel.api.models.dto.response.LoginResponse
import org.springframework.stereotype.Service

@Service
interface AuthService {
    fun createUser(name: String, surnames: String, email: String, rawPassword: String, role: Role): UserDTO
    fun login(loginRequest: LoginRequest): LoginResponse
}
