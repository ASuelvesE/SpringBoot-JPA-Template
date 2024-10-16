package com.angel.api.services

import com.angel.api.models.dto.UserDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserService {
    fun getById(id: UUID): UserDTO
    fun getByEmail(email: String): UserDTO
    fun findAllPaginated(pageable: Pageable): Page<UserDTO>
}
