package com.angel.api.services

import com.angel.api.models.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserService {
    fun getById(id: UUID): User
    fun getByEmail(email: String): User
    fun findAllPaginated(pageable: Pageable): Page<User>
}
