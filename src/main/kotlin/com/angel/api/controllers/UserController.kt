package com.angel.api.controllers

import com.angel.api.models.dto.UserDTO
import com.angel.api.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/apiv1/users")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @Operation(summary = "Get user by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User found"),
            ApiResponse(responseCode = "404", description = "User not found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<UserDTO> {
        try {
            val user = userService.getById(id)
            return ResponseEntity.ok(user)
        } catch (e: Exception) {
            throw e
        }
    }

    @Operation(summary = "Get user by email")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User found"),
            ApiResponse(responseCode = "404", description = "User not found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @GetMapping("/email")
    fun getByEmail(@RequestParam email: String): ResponseEntity<UserDTO> {
        try {
            val user = userService.getByEmail(email)
            return ResponseEntity.ok(user)
        } catch (e: Exception) {
            throw e
        }
    }

    @Operation(summary = "Find all users with pagination")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Users found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @GetMapping
    fun findAllPaginated(pageable: Pageable): ResponseEntity<Page<UserDTO>> {
        try {
            return ResponseEntity.ok(userService.findAllPaginated(pageable))
        } catch (e: Exception) {
            throw e
        }
    }
}
