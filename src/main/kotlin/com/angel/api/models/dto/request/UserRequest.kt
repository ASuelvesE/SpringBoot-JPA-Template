package com.angel.api.models.dto.request

import com.angel.api.models.enums.Role

data class UserRequest(
    val name: String,
    val surnames: String,
    val email: String,
    val password: String,
    val role: Role = Role.CUSTOMER
)