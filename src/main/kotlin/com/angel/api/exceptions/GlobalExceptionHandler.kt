package com.angel.api.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    // Maneja ApiException
    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException): ResponseEntity<Map<String, Any>> {
        val response: Map<String, Any> = mapOf(
            "timestamp" to System.currentTimeMillis(),  // Añadir un timestamp
            "status" to ex.status.value(),  // Estado HTTP
            "code" to (ex.code ?: "No code available"),  // Código específico de la excepción
            "message" to (ex.message ?: "No message available")  // Mensaje de la excepción
        )
        return ResponseEntity(response, ex.status)
    }
}
