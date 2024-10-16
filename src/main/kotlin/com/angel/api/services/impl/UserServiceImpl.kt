package com.angel.api.services.impl

import com.angel.api.configs.SessionManager
import com.angel.api.converters.UserDataConverter
import com.angel.api.exceptions.ApiException
import com.angel.api.models.User
import com.angel.api.models.dto.UserDTO
import com.angel.api.repositories.UserRepository
import com.angel.api.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl : UserService, UserDetailsService {

    @Autowired
    lateinit var sessionManager: SessionManager

    @Autowired
    lateinit var userRepository: UserRepository

    override fun getById(id: UUID): UserDTO {
        try {
            val user = userRepository.findById(id).orElseThrow {
                ApiException(HttpStatus.NOT_FOUND, "80/404", "User not found with ID: $id")
            }
            return UserDataConverter.toDTO(user)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getByEmail(email: String): UserDTO {
        try {
            println("Token: ${sessionManager.getToken()}")
            val user = userRepository.findByEmail(email) ?: throw ApiException(HttpStatus.NOT_FOUND, "80/404", "User not found with email: $email")
            return UserDataConverter.toDTO(user)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun findAllPaginated(pageable: Pageable): Page<UserDTO> {
        try {
            val page = userRepository.findAll(pageable)
            return page.map { UserDataConverter.toDTO(it) }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw ApiException(HttpStatus.NOT_FOUND, "80/404", "User not found with email: $email")
        return org.springframework.security.core.userdetails.User(
            user.email,
            "",
            user.authorities
        )
    }
}
