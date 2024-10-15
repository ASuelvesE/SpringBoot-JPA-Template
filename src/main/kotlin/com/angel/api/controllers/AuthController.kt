package com.angel.api.controllers

import com.angel.api.models.User
import com.angel.api.models.dto.request.LoginRequest
import com.angel.api.models.dto.request.UserRequest
import com.angel.api.models.dto.response.LoginResponse
import com.angel.api.exceptions.ApiException
import com.angel.api.services.AuthService
import com.angel.api.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/apiv1/auth")
class AuthController() {

    @Autowired
    lateinit var authService: AuthService


    @PostMapping("/register")
    fun register(@RequestBody userRequest: UserRequest): ResponseEntity<User> {
        try {
            val user = authService.createUser(userRequest.name, userRequest.surnames, userRequest.email, userRequest.password, userRequest.role)
            return ResponseEntity.ok(user)
        }catch (e: Exception){
            throw e
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        try {
            return ResponseEntity.ok(authService.login(loginRequest))
        }catch (e: Exception){
            throw e
        }
    }
}