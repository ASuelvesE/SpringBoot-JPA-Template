package com.angel.api.models.dto.response

data class LoginResponse(
    val email: String = "",
    val password: String = "",
    val token: String = ""
)