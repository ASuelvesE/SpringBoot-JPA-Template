package com.angel.api.infrastructure.exceptions

import org.springframework.http.HttpStatus

data class ApiException(
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
    val code: String? = "",
    override val message: String? = ""
) : RuntimeException(message)
