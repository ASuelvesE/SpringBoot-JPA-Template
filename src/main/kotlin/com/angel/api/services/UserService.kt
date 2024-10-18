package com.angel.api.services

import com.angel.api.models.dto.UserDTO
import org.springframework.data.domain.Page
import java.util.*

interface UserService {
    fun update(userDTO: UserDTO): UserDTO
    fun getById(id: UUID): UserDTO
    fun getByEmail(email: String): UserDTO
    fun findAllPaginated(offset: Int, limit: Int): Page<UserDTO>
}
