package com.angel.api.models.dto

import com.angel.api.models.enums.Role
import java.util.UUID

data class UserDTO(
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    val surnames: String = "",
    val email: String = "",
    val role: Role = Role.CUSTOMER
)
