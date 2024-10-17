package com.angel.api.controllers

import com.angel.api.models.User
import com.angel.api.models.dto.UserDTO
import com.angel.api.models.dto.request.LoginRequest
import com.angel.api.models.dto.request.UserRequest
import com.angel.api.models.dto.response.LoginResponse
import com.angel.api.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

@RestController
@RequestMapping("/api/v1/auth")
class AuthController() {

    @Autowired
    lateinit var authService: AuthService

    @Operation(summary = "Register a new user")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User registered successfully"),
            ApiResponse(responseCode = "400", description = "Invalid user data"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @PostMapping("/register")
    fun register(@RequestBody userRequest: UserRequest): ResponseEntity<UserDTO> {
        try {
            val user = authService.createUser(userRequest.name, userRequest.surnames, userRequest.email, userRequest.password, userRequest.role)
            return ResponseEntity.ok(user)
        } catch (e: Exception) {
            throw e
        }
    }

    @Operation(summary = "Login a user")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Login successful"),
            ApiResponse(responseCode = "401", description = "Unauthorized, incorrect email/password"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        try {
            return ResponseEntity.ok(authService.login(loginRequest))
        } catch (e: Exception) {
            throw e
        }
    }
}
