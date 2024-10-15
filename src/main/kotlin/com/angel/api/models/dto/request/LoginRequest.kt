package com.angel.api.models.dto.request

data class LoginRequest(
    val email: String,
    val password: String
)