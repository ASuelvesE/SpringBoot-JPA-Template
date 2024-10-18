package com.angel.api.controllers

import com.angel.api.models.dto.UserDTO
import com.angel.api.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/users")
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
    fun getById(@PathVariable(required = true) id: UUID): ResponseEntity<UserDTO> {
        try {
            val user = userService.getById(id)
            return ResponseEntity.ok(user)
        } catch (e: Exception) {
            throw e
        }
    }

    @Operation(summary = "Update user")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User updated"),
            ApiResponse(responseCode = "404", description = "User not found"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @PutMapping("/")
    fun update(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        try {
            val user = userService.update(userDTO)
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
    fun getByEmail(@RequestParam(value = "email", required = true) email: String): ResponseEntity<UserDTO> {
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
            ApiResponse(responseCode = "400", description = "Bad Request - invalid parameters"),
            ApiResponse(responseCode = "500", description = "Internal server error")
        ]
    )
    @GetMapping
    fun findAllPaginated(
        @RequestParam(value = "offset", required = true) offset: Int,
        @RequestParam(value = "limit", required = true) limit: Int
    ): ResponseEntity<Page<UserDTO>> {
        try {
            return ResponseEntity.ok(userService.findAllPaginated(offset, limit))
        } catch (e: Exception) {
            throw e
        }
    }
}
