package com.angel.api.services.impl

import com.angel.api.exceptions.ApiException
import com.angel.api.models.User
import com.angel.api.repositories.UserRepository
import com.angel.api.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun getById(id: UUID): User {
        return try {
            userRepository.findById(id).orElseThrow {
                ApiException(HttpStatus.NOT_FOUND, "80/404", "User not found with ID: $id")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getByEmail(email: String): User {
        return try {
            userRepository.findByEmail(email) ?: throw ApiException(HttpStatus.NOT_FOUND, "80/404", "User not found with email: $email")
        } catch (e: Exception) {
            throw e
        }
    }

    override fun findAllPaginated(pageable: Pageable): Page<User> {
        return try {
            userRepository.findAll(pageable)
        } catch (e: Exception) {
            throw e
        }
    }
}
